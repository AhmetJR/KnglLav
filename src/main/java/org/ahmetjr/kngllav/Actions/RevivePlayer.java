 package org.ahmetjr.kngllav.Actions;
 
 import org.bukkit.Bukkit;
 import org.bukkit.entity.Player;
 
 public class RevivePlayer
   implements ThoseAffected
 {
   public void allPlayers() {
     for (Player player : Bukkit.getOnlinePlayers()) {
       player.setHealth(player.getMaxHealth());
      player.setFoodLevel(20);
      //color list name palyer send message
     } 
   }
 }

