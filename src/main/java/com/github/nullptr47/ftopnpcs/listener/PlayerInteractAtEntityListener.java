package com.github.nullptr47.ftopnpcs.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    void on(PlayerInteractAtEntityEvent event) {

        if (event.getRightClicked().getType() == EntityType.ARMOR_STAND && event.getRightClicked().hasMetadata("special"))
            event.setCancelled(true);

    }

}
