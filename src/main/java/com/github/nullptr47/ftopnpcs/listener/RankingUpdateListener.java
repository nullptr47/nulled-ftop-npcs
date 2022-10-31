package com.github.nullptr47.ftopnpcs.listener;

import br.com.kickpost.ftop.events.FactionsTopGeneralUpdateEvent;
import com.github.nullptr47.ftopnpcs.manager.NpcsManager;
import com.github.nullptr47.ftopnpcs.model.RankingNPC;
import com.massivecraft.factions.entity.Faction;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Map;

@RequiredArgsConstructor
public class RankingUpdateListener implements Listener {

    private final NpcsManager npcsManager;
    private final Plugin plugin;

    @EventHandler
    void on(FactionsTopGeneralUpdateEvent event) {

        Bukkit.getScheduler().runTask(plugin, (() -> {

            Map.Entry<Faction, Double[]> faction;
            RankingNPC npc;

            for (Map.Entry<Location, RankingNPC> entry : npcsManager.getNpcs().entrySet()) {

                npc = entry.getValue();
                faction = event.getTopFactions().get(npc.getPosition() - 1);

                npc.getHologram().remove();

                npcsManager.setupHologram(npc.getHologram(), faction.getKey(), npc.getPosition(),
                        Arrays.stream(faction.getValue()).mapToDouble(a -> a).sum());
                npcsManager.setupEntity(npc.getEntity(), faction.getKey(), npc.getPosition());

                npc.getHologram().display();

            }

        }));
        
    }

}
