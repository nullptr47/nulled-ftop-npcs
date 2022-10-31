package com.github.nullptr47.ftopnpcs.command;

import com.github.nullptr47.ftopnpcs.manager.NpcsManager;
import com.github.nullptr47.ftopnpcs.util.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;

public class SetNPCCommand extends Command {

    private final NpcsManager npcsManager;

    public SetNPCCommand(NpcsManager npcsManager) {

        super("setnpc");

        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        SimpleCommandMap simpleCommandMap = craftServer.getCommandMap();

        simpleCommandMap.register("nullptr47", this);

        this.npcsManager = npcsManager;

    }

    public boolean execute(CommandSender commandSender, String s, String[] arguments) {

        if (commandSender instanceof ConsoleCommandSender) {

            commandSender.sendMessage(ChatColor.RED + "Este comando só pode ser usado por players.");
            return false;

        }

        if (!commandSender.hasPermission("ftopnpcs.set")) {

            commandSender.sendMessage(ChatColor.RED + "Você não pode fazer isto.");
            return false;

        }

        Player player = (Player) commandSender;

        if (arguments.length != 1) {

            player.sendMessage(ChatColor.RED + "Use: /setnpc <posição>.");
            return false;

        }

        Integer position = NumberUtils.integerParser(arguments[0]);

        if (position == null) {

            player.sendMessage(ChatColor.RED + "Posição inválida.");
            return false;

        }

        npcsManager.createNPC(((Player) commandSender).getLocation(), position, false);

        commandSender.sendMessage(ChatColor.GREEN + "Yay! NPC criado com sucesso!");

        return false;
    }
}
