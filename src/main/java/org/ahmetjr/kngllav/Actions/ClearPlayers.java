 package org.ahmetjr.kngllav.Actions;
 
 import org.bukkit.Bukkit;
 import org.bukkit.entity.Player;
 
 public class ClearPlayers
   implements ThoseAffected
 {
   public void allPlayers() {
     for (Player player : Bukkit.getOnlinePlayers())
       player.getInventory().clear();
   }
 }
