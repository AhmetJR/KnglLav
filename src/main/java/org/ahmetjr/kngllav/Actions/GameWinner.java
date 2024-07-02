package org.ahmetjr.kngllav.Actions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.ahmetjr.kngllav.Events.GameHandler;
import org.ahmetjr.kngllav.Main;

public class GameWinner implements Listener {
    private int survivors;
    private Player lastSurvivor;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Kısa bir gecikmeyle oyuncu sayısını kontrol ediyoruz
        Bukkit.getScheduler().runTaskLater(Main.instance, this::checkPlayers, 1L);
    }

    private void checkPlayers() {
        survivors = 0;
        lastSurvivor = null;

        // Tüm online oyuncuları kontrol ediyoruz
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                survivors++;
                lastSurvivor = player;
            }
        }

        if (Main.instance != null) {
            switch (survivors) {
                case 2:
                    Main.instance.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bBüyük Final, Geriye Son &c2 &bKişi Kaldı."));
                    break;
                case 1:
                    Main.instance.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aVeee Kazanaaan &6" + lastSurvivor.getName()));
                    GameHandler.gameReady = false;
                    GameHandler.gameStatus = false;
                    launchFireworks(lastSurvivor);
                    break;
                default:
                    Main.instance.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eGeriye Son &c" + survivors + " &eKişi Kaldı."));
                    break;
            }
        } else {
            Bukkit.getLogger().severe("Main.instance is null!");
        }
    }

    private void launchFireworks(Player winner) {
        Firework fw = (Firework) winner.getWorld().spawnEntity(winner.getLocation().add(0.0D, 3.0D, 0.0D), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();

        for (int i = 0; i < 5; i++) {
            Firework fw2 = (Firework) winner.getWorld().spawnEntity(winner.getLocation().add(0.0D, 3.0D, 0.0D), EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
            Bukkit.getScheduler().runTaskLater(Main.instance, fw2::detonate, 20L * (i + 1));
        }
    }
}
