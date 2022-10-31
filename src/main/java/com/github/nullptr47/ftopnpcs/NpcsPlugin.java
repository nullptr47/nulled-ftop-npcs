package com.github.nullptr47.ftopnpcs;

import com.github.nullptr47.ftopnpcs.command.SetNPCCommand;
import com.github.nullptr47.ftopnpcs.dao.NpcsDAO;
import com.github.nullptr47.ftopnpcs.listener.PlayerInteractAtEntityListener;
import com.github.nullptr47.ftopnpcs.listener.RankingUpdateListener;
import com.github.nullptr47.ftopnpcs.manager.NpcsManager;
import com.github.nullptr47.ftopnpcs.model.RankingNPC;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NpcsPlugin extends JavaPlugin {

    public static NpcsPlugin getInstance() {

        return JavaPlugin.getPlugin(NpcsPlugin.class);

    }

    @Getter private NpcsManager npcsManager;
    @Getter private NpcsDAO npcsDAO;

    public void onLoad() { saveDefaultConfig(); }

    public void onEnable() {

        npcsManager = new NpcsManager(getConfig());
        npcsDAO = new NpcsDAO(this);

        Bukkit.getPluginManager().registerEvents(new RankingUpdateListener(npcsManager, this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);

        new SetNPCCommand(npcsManager);

        npcsDAO.loadAll(npcsManager);

    }

    public void onDisable() {

        for (RankingNPC npc : npcsManager.getNpcs().values()) {

            npc.getHologram().remove();
            npc.getEntity().remove();

        }

    }
}
