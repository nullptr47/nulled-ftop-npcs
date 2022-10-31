package com.github.nullptr47.ftopnpcs.dao;

import com.github.nullptr47.ftopnpcs.manager.NpcsManager;
import com.github.nullptr47.ftopnpcs.model.RankingNPC;
import com.github.nullptr47.ftopnpcs.util.LocationSerializer;
import com.massivecraft.massivecore.xlib.guava.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class NpcsDAO {

    private Connection connection;

    public NpcsDAO(Plugin plugin) {

        try {

            File file = new File(plugin.getDataFolder(), "database.db");

            if (!file.exists()) file.createNewFile();

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);

            init();

        }

        catch (Exception exception) {

            Bukkit.getLogger().log(Level.SEVERE, ChatColor.DARK_RED + "Ocorreu um erro ao criar a database.");
            exception.printStackTrace();

        }

    }

    public void init() {

        try (PreparedStatement statement = connection.prepareStatement("create table if not exists NPCS(location text, position smallint)")) {

            statement.executeUpdate();

        }

        catch (Exception exception) { exception.printStackTrace(); }

    }

    public void loadAll(NpcsManager npcsManager) {

        try (PreparedStatement statement = connection.prepareStatement("select * from NPCS")) {

            try (ResultSet result = statement.executeQuery()) {

                while (result.next())
                    npcsManager.createNPC(LocationSerializer.fromText(result.getString("location")),
                            result.getInt("position"), true);

            }

        }

        catch (Exception exception) {

            exception.printStackTrace();

            Bukkit.getLogger().log(Level.SEVERE, "An error has occurred when loading npcs.");

        }

    }

    /**
     * saves npc info
     * @param location npc's location.
     * @param position ranking position
     */
    public void saveNPC(Location location, int position) {

        /**
         * mysql doesnt recognizes Location objets
         * soo we need to serialize into a string
         */
        String locationText = LocationSerializer.toText(location);

        try (PreparedStatement statement = connection.prepareStatement("insert into NPCS(location, position) values(?, ?)")) {

            statement.setString(1, locationText);
            statement.setInt(2, position);

            statement.executeUpdate();

        }

        catch (Exception exception) {

            exception.printStackTrace();

            Bukkit.getLogger().log(Level.SEVERE, String.format("An error has occurred when saving npc for location %s.", locationText));

        }

    }

    /**
     * deletes a npc record
     * @param location npc's location
     */
    public void removeNPC(Location location) {

        /**
         * mysql doesnt recognizes Location objets
         * soo we need to serialize into a string
         */
        String locationText = LocationSerializer.toText(location);

        try (PreparedStatement statement = connection.prepareStatement("delete from NPCS where location = ?")) {

            statement.setString(1, locationText);

            statement.executeUpdate();

        }

        catch (Exception exception) {

            exception.printStackTrace();

            Bukkit.getLogger().log(Level.SEVERE, String.format("An error has occurred when deleting npc for %s.", locationText));

        }

    }

}
