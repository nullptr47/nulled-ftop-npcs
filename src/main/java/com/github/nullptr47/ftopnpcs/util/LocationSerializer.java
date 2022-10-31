package com.github.nullptr47.ftopnpcs.util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerializer {

    private static final String BASE = "{x}_{y}_{z}_{yaw}_{pitch}_{world}";
    private static final String[] BASE_HOLDER = new String[] { "{x}" , "{y}" , "{z}" , "{yaw}" , "{pitch}" , "{world}" } ;

    public static String toText(Location location) {

        return StringUtils.replaceEach(BASE, BASE_HOLDER, new String[] {
                String.valueOf(location.getX()), String.valueOf(location.getY()), String.valueOf(location.getZ()),
                String.valueOf(location.getYaw()), String.valueOf(location.getPitch()), location.getWorld().getName()
        });

    }

    public static Location fromText(String string) {

        String[] splitter = StringUtils.split(string, '_');

        return new Location(Bukkit.getWorld(splitter[5]),
                Double.parseDouble(splitter[0]), Double.parseDouble(splitter[1]), Double.parseDouble(splitter[2]),
                Float.parseFloat(splitter[3]), Float.parseFloat(splitter[4]));

    }

}
