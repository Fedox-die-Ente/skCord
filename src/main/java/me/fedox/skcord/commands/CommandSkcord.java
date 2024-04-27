package me.fedox.skcord.commands;

import me.fedox.skcord.SkCord;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandSkcord implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage("This server is running skCord v2.2");
        commandSender.sendMessage("skCord is a plugin that allows you to connect your Minecraft server to your Discord server via webhooks.");
        return false;
    }
}
