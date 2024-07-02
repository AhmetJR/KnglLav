package org.ahmetjr.kngllav.Actions;

import org.ahmetjr.kngllav.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.ahmetjr.kngllav.Events.GameHandler;
import org.ahmetjr.kngllav.Files.Config;
import org.bukkit.potion.PotionEffectType;

public class Timer implements ThoseAffected, Runnable {
    private BossBar bar = Bukkit.createBossBar(null, BarColor.YELLOW, BarStyle.SEGMENTED_10);
    private int time = Config.get().getInt("OyunBaşlamaSüresi") * 60;
    private double timeRemaining = 1.0D / this.time;
    private boolean pvpOpened = false; // Flag to ensure PVP is opened only once

    public void allPlayers() {
        Timer timer = new Timer();
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, timer);
    }

    public void run() {
        Bukkit.getScheduler().runTaskTimer(Main.instance, new Runnable() {
            @Override
            public void run() {
                if (time <= 0) {
                    if (!pvpOpened) { // Check if PVP is already opened
                        Bukkit.getScheduler().runTask(Main.instance, new Runnable() {
                            @Override
                            public void run() {
                                GameHandler.gameStatus = true;
                                updateBossBar("§aPVP Açıldı", 1.0D, BarColor.GREEN);
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&eKngl&Lav §7§l» §aPVP AÇILDI!"));
                                bar.removeAll();
                                updateBossBar("§eLav Yükseliyor!!!", 1.0D, BarColor.YELLOW);
                                bar.removeAll();
                            }
                        });
                        pvpOpened = true; // Set the flag to true
                    }
                    Bukkit.getScheduler().cancelTask(this.hashCode());
                    return;
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    switch (time) {
                        case 60:
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eKngl&Lav §7§l» §eLavın Yükselmesine Son §c60 Saniye §eKaldı."));
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                            break;
                        case 30:
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eKngl&Lav §7§l» §eLavın Yükselmesine Son §c30 Saniye §eKaldı."));
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                            break;
                        case 10:
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eKngl&Lav §7§l» §eLavın Yükselmesine Son §c10 Saniye §eKaldı."));
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                            break;
                        case 5:
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eKngl&Lav §7§l» §eLavın Yükselmesine Son §c5 Saniye §eKaldı."));
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eKngl&Lav §7§l» §6Ateş Koruman Kalktı!!"));
                            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                            break;
                        case 1:
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eKngl&Lav §7§l» §6Lav Yükseliyor!!!"));
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                            break;
                        default:
                            break;
                    }
                }

                time--;
                Bukkit.getScheduler().runTask(Main.instance, new Runnable() {
                    @Override
                    public void run() {
                        bossBar();
                    }
                });
            }
        }, 0L, 20L); // 20L equals 1 second
    }

    public void bossBar() {
        bar.setTitle("§eKalan Süre §6" + (time / 60) + " Dakika " + (time % 60) + " Saniye");
        bar.setProgress(bar.getProgress() - timeRemaining);
        bar.setVisible(true);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }
        if (time <= 1) bar.removeAll();
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
