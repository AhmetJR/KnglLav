package org.ahmetjr.kngllav;

import org.ahmetjr.kngllav.Commands.*;
import org.ahmetjr.kngllav.Actions.GameWinner;
import org.ahmetjr.kngllav.Events.GameHandler;
import org.ahmetjr.kngllav.Files.Config;
import org.ahmetjr.kngllav.Files.Setup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {
    private final HashMap<UUID, BukkitTask> playerTasks = new HashMap<>();
    public static Main instance;
    private Location spawn;
    private LavaInfo lavaInfo;
    private int size;
    private int startLevel;

    @Override
    public void onEnable() {
        Setup setup = new Setup();
        setup.config();

        instance = this;

        this.size = Config.get().getInt("BorderGenişliği");
        this.startLevel = Config.get().getInt("BaşlangıçYüksekliği");

        String blockName = Config.get().getString("YükselecekBlok");
        if (Material.getMaterial(blockName) == null) {
            getLogger().info("§6" + blockName + " §aYükselecek Blok Olarak Ayarlandı.");
            Config.get().set("YükselecekBlok", "LAVA");
        }

        this.spawn = getServer().getWorlds().get(0).getSpawnLocation();

        World world = getServer().getWorlds().get(0);
        world.getWorldBorder().setCenter(this.spawn);
        world.getWorldBorder().setSize(this.size);

        Location bottomRight = this.spawn.clone().subtract(this.size / 2.0D, 0.0D, this.size / 2.0D);
        Location topLeft = this.spawn.clone().add(this.size / 2.0D, 0.0D, this.size / 2.0D);

        this.lavaInfo = new LavaInfo(bottomRight, topLeft, this.startLevel);

        getCommand("başlat").setExecutor((CommandExecutor) new Start());
        getCommand("ayarla").setExecutor((CommandExecutor) new Settings());
        getCommand("kanat").setExecutor((CommandExecutor) new Elytra());
        getCommand("alan").setExecutor((CommandExecutor) new Border());
        getCommand("baraç").setExecutor((CommandExecutor) new org.ahmetjr.kngllav.Commands.Action(this));
        getCommand("barkapat").setExecutor((CommandExecutor) new org.ahmetjr.kngllav.Commands.ActionOff(this));
        getServer().getPluginManager().registerEvents((Listener) new Start(), this);
        getServer().getPluginManager().registerEvents((Listener) new GameWinner(), this);
        getServer().getPluginManager().registerEvents((Listener) new GameHandler(), this);

//        getServer().getPluginManager().registerEvents((Listener) new Book(), this);
    }
    @Override
    public void onDisable() {
        for (BukkitTask task : playerTasks.values()) {
            task.cancel();
        }
        playerTasks.clear();
    }

    public LavaInfo getLavaInfo() {
        return this.lavaInfo;
    }

    public Material getBlock() {
        return Material.getMaterial(Config.get().getString("YükselecekBlok"));
    }

    public void sendMessage(String msg) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&eKngl&Lav &7&l» &f" + msg));
    }
    public HashMap<UUID, BukkitTask> getPlayerTasks() {
        return playerTasks;
    }
}
