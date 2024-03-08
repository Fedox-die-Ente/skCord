package me.fedox.skcord;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import java.io.IOException;
import java.util.Objects;

import me.fedox.skcord.commands.CommandSkcord;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

public final class SkCord
        extends JavaPlugin {
    SkCord instance;
    SkriptAddon addon;

    public void onEnable() {
        this.instance = this;
        this.addon = Skript.registerAddon(this);
        try {
            this.addon.loadClasses("me.fedox.skcord", "elements");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("SkCord has been enabled!");
        this.registerCommands();

        this.loadMetrics();
    }

    public SkCord getInstance() {
        return this.instance;
    }

    public SkriptAddon getAddonInstance() {
        return this.addon;
    }

    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("skcord")).setExecutor(new CommandSkcord());
    }

    private void loadMetrics() {
        Metrics metrics = new Metrics(this, 20590);
        metrics.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));
    }
}