package org.ahmetjr.kngllav.Events;

import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GameHandler
  implements Listener {
  public static boolean gameReady = false;
  public static boolean gameStatus = false;
  public static boolean gameFinished = false;
  
  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
     Player player = event.getPlayer();
              player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 1000000, 1));
     if (gameReady) {
       player.setGameMode(GameMode.SPECTATOR);
    } else {
      player.setGameMode(GameMode.ADVENTURE);
       player.teleport(randomLocation(player));
       player.getInventory().clear();
    } 
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent event) {
     Player player = event.getPlayer();
     if (!gameReady) {
       Location location = player.getLocation().add(0.0D, -1.0D, 0.0D);
      if (!location.getBlock().getType().equals(Material.AIR) &&
        event.getFrom().getZ() != event.getTo().getZ() && (
         player.getGameMode() != GameMode.SPECTATOR || player.getGameMode() != GameMode.CREATIVE)) {
          player.sendTitle("Oyun Başlamadı!", "Oyun başlamadan hareket edemezsiniz.", 10, 70, 20);
        event.setCancelled(true);
      }
    }
    else {
      
      event.setCancelled(false);
    } 
  }
  
  @EventHandler
  public void onFallDamage(EntityDamageEvent event) {
    if (event.getEntity() instanceof Player &&
      event.getCause() == EntityDamageEvent.DamageCause.FALL &&
       !gameReady) {
       event.setCancelled(true);
    }
  }


  
  @EventHandler
  public void onPVP(EntityDamageEvent event) {
     if (event.getEntity() instanceof Player && (
       event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event
       .getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK || event
      .getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event
       .getCause() == EntityDamageEvent.DamageCause.PROJECTILE) &&
       !gameStatus) {
      event.setCancelled(true);
    }
  }


  
  @EventHandler
  public void onDeath(PlayerDeathEvent event) {
     Player player = event.getEntity().getPlayer();
     if (gameStatus) {
       player.setGameMode(GameMode.SPECTATOR);
     } else if (gameReady) {
       player.setGameMode(GameMode.SPECTATOR);
     } else if (!gameStatus) {
       player.setGameMode(GameMode.SPECTATOR);
    } 
  }
  
  public Location randomLocation(Player player) {
     Random random = new Random();
     int x = random.nextInt(25);
     int y = 200;
     int z = random.nextInt(25);


    
     Location randomLocation = new Location(player.getWorld(), player.getWorld().getSpawnLocation().getX() + x, y, player.getWorld().getSpawnLocation().getZ() + z);
     return randomLocation;
  }
}


