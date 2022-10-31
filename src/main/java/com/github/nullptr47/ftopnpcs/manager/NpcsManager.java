package com.github.nullptr47.ftopnpcs.manager;

import com.github.nullptr47.ftopnpcs.NpcsPlugin;
import com.github.nullptr47.ftopnpcs.model.RankingNPC;
import com.github.nullptr47.ftopnpcs.util.FactionUtils;
import com.github.nullptr47.ftopnpcs.util.ItemBuilder;
import com.github.nullptr47.holograms.Hologram;
import com.github.nullptr47.holograms.Holograms;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.massivecraft.factions.entity.Faction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Map;
import java.util.stream.Collectors;

import static com.github.nullptr47.ftopnpcs.util.NumberUtils.numberFormat;
import static com.github.nullptr47.ftopnpcs.util.NumberUtils.numberKFormat;

@RequiredArgsConstructor
public class NpcsManager {

    static final String[] placeholders = new String[] { "&", "%pos%", "%tag%", "%name%", "%value%", "%value_k%" };
    static final ItemStack[] equipment = new ItemStack[] {

            new ItemStack(Material.DIAMOND_CHESTPLATE),
            new ItemStack(Material.DIAMOND_LEGGINGS),
            new ItemStack(Material.DIAMOND_BOOTS),

            new ItemStack(Material.GOLD_CHESTPLATE),
            new ItemStack(Material.GOLD_LEGGINGS),
            new ItemStack(Material.GOLD_BOOTS),

            new ItemStack(Material.IRON_CHESTPLATE),
            new ItemStack(Material.IRON_LEGGINGS),
            new ItemStack(Material.IRON_BOOTS),

            new ItemStack(Material.LEATHER_CHESTPLATE),
            new ItemStack(Material.LEATHER_LEGGINGS),
            new ItemStack(Material.LEATHER_BOOTS),

    };

    private final FileConfiguration configuration;
    @Getter private final Map<Location, RankingNPC> npcs = Maps.newHashMap();

    public void createNPC(Location location, int position, boolean exists) {

        Faction faction = FactionUtils.getFactionByPosition(position);
        ArmorStand entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        Hologram hologram = Holograms.newHologram(location.clone().add(0, 0.85, 0), Lists.newLinkedList());

        setupEntity(entity, faction, position);
        setupHologram(hologram, faction, position, null);

        if (!exists)
            NpcsPlugin.getInstance().getNpcsDAO().saveNPC(location, position);

        npcs.put(location, RankingNPC.builder().entity(entity).hologram(hologram).position(position).build());

        hologram.display();

    }

    public void setupEntity(ArmorStand entity, Faction faction, int position) {

        entity.setHelmet(ItemBuilder.of(Material.SKULL_ITEM)
                .durability((short) 3)
                .owner(faction.getLeader() == null ? configuration.getString("npc.default-skin") : faction.getLeader().getName())
                .build());

        int index = getIndex(position);

        entity.setChestplate(equipment[index++]);
        entity.setLeggings(equipment[index++]);
        entity.setBoots(equipment[index]);

        entity.setMetadata("special", new FixedMetadataValue(NpcsPlugin.getInstance(), true));
        entity.setSmall(configuration.getString("npc.type").equals("MINI_ARMOR_STAND"));
        entity.setArms(false);
        entity.setGravity(false);
        entity.setCustomNameVisible(false);

    }

    public void setupHologram(Hologram hologram, Faction faction, int position, Double value) {

        if (value == null) value = FactionUtils.getValue(faction);

        String[] replacements = faction.isNone() ? new String[]{} :
                new String[] {
                        "§", String.valueOf(position), faction.getTag(), faction.getName(), numberFormat(value), numberKFormat(value)
        };

        hologram.setLines(configuration.getStringList("hologram." + (faction.isNone() ? "undefined" : "lines"))
                .stream()
                .map(line -> StringUtils.replaceEach(line, placeholders, replacements))
                .collect(Collectors.toList()));

    }

    private int getIndex(int position) {

        switch (position) {

            case 1:
                return 0;

            case 2:
                return 2;

            case 3:
                return 5;

            default:
                return 8;

        }

    }

}
