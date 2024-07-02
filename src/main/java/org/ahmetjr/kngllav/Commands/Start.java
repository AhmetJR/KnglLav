package org.ahmetjr.kngllav.Commands;

import org.ahmetjr.kngllav.Actions.*;
import org.ahmetjr.kngllav.Donate.Book;
import org.ahmetjr.kngllav.Events.GameHandler;
import org.ahmetjr.kngllav.Files.Config;
import org.ahmetjr.kngllav.LavaInfo;
import org.ahmetjr.kngllav.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

public class Start implements CommandExecutor, Listener {
    private final int lavRisingDelay = Config.get().getInt("YükselmeAralığı");
    private final int gameStartingTime = Config.get().getInt("OyunBaşlamaSüresi") * 60;

    private final BossBar bar = Bukkit.createBossBar(null, BarColor.YELLOW, BarStyle.SEGMENTED_10);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (!player.hasPermission("lavarising.start")) return false;

        player.chat("/whitelist on");

        if (GameHandler.gameStatus) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&Lava&eRisingJR &7&l» &6 Zaten Bir Oyun Oynanıyor."));
            return false;
        }

        startGame();
        return true;
    }

    private void startGame() {
        new Timer().allPlayers();
        new ClearPlayers().allPlayers();
        new GivePickaxe().allPlayers();
        new RevivePlayer().allPlayers();
        GameHandler.gameReady = true;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.removePotionEffect(PotionEffectType.WATER_BREATHING);
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
            doLava();
            new Book().removeBook();
        }, 20L * gameStartingTime);
    }

    private void doLava() {


        for (int i = lavRisingDelay; i > 0; i--) {
            long delay = (lavRisingDelay - i) * 20L;
            scheduleBossBarUpdate("§e" + i + " Saniye Sonra Lav Yükselecek!", 1.0D, delay);
        }

//        updateBossBar("§e10 Saniye Sonra Lav Yükselecek!", 1.0D, BarColor.YELLOW);
//        scheduleBossBarUpdate("§e5 Saniye Sonra Lav Yükselecek!", 1.0D, 100L);
//        scheduleBossBarUpdate("§e4 Saniye Sonra Lav Yükselecek!", 0.8D, 120L);
//        scheduleBossBarUpdate("§e3 Saniye Sonra Lav Yükselecek!", 0.6D, 140L);
//        scheduleBossBarUpdate("§e2 Saniye Sonra Lav Yükselecek!", 0.4D, 160L);
//        scheduleBossBarUpdate("§e1 Saniye Sonra Lav Yükselecek!", 0.2D, 180L);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
            updateBossBar("§cLav Bir Kademe Daha Yükseldi!", 1.0D, BarColor.RED);
            handleLavaRise();
        }, lavRisingDelay * 20L);
    }

    private void handleLavaRise() {
        LavaInfo lavaInfo = Main.instance.getLavaInfo();

        // LavaInfo null kontrolü
        if (lavaInfo == null) {
            Bukkit.getLogger().warning("LavaInfo null, handleLavaRise işlemi iptal edildi.");
            return;
        }

        Location edgeMin = lavaInfo.bottomRight;
        Location edgeMax = lavaInfo.topLeft;
        World world = edgeMin.getWorld();

        // edgeMin veya edgeMax null kontrolü (muhtemelen lavaInfo null olduğunda)
        if (edgeMin == null || edgeMax == null || world == null) {
            Bukkit.getLogger().warning("edgeMin, edgeMax veya world null, handleLavaRise işlemi iptal edildi.");
            return;
        }

        for (int x = edgeMin.getBlockX(); x <= edgeMax.getBlockX(); x++) {
            for (int y = edgeMin.getBlockY(); y <= edgeMax.getBlockY(); y++) {
                for (int z = edgeMin.getBlockZ(); z <= edgeMax.getBlockZ(); z++) {
                    Block block = new Location(world, x, y, z).getBlock();

                    // Block null kontrolü
                    if (block == null) {
                        Bukkit.getLogger().warning("Block null, handleLavaRise işlemi iptal edildi.");
                        continue;
                    }

                    if (block.getType() == Material.AIR) {
                        block.setType(Material.LAVA);
                    }
                }
            }
        }

        // Oyuncuların kontrol edilmesi ve işlemler
        if (edgeMax.getBlockY() >= 20 && edgeMax.getBlockY() < 255) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getGameMode() == GameMode.SURVIVAL && player.getLocation().getBlockY() <= edgeMax.getBlockY() - 1) {
                    player.playSound(player.getLocation(), Sound.ENTITY_GHAST_HURT, 1.0F, 1.0F);
                    player.damage(player.getHealth());
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&eKngl&Lav &7&l» " + ChatColor.YELLOW + player.getName() + " §cAdlı Oyuncu lav altında olduğu için sistem tarafından elendi!"));
                }
            }
        }

        lavaInfo.IncreaseCurrentLevel();
        bar.removeAll();

        if (GameHandler.gameStatus) {
            lavaTimer();
        }
    }

    private void lavaTimer() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, this::doLava, 20L * lavRisingDelay);
    }

    private void scheduleBossBarUpdate(String title, double progress, long delay) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, () -> updateBossBar(title, progress), delay);
    }

    private void updateBossBar(String title, double progress) {
        updateBossBar(title, progress, BarColor.YELLOW);
    }

    private void updateBossBar(String title, double progress, BarColor color) {
        bar.setTitle(title);
        bar.setColor(color);
        bar.setProgress(progress);
        bar.setVisible(true);

        for (Player player : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }
    }
}
