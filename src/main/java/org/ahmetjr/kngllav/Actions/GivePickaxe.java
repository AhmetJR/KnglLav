 package org.ahmetjr.kngllav.Actions;
 
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.Material;
 import org.bukkit.Sound;
 import org.bukkit.enchantments.Enchantment;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.meta.ItemMeta;
 import org.ahmetjr.kngllav.Files.Config;
 import org.bukkit.potion.PotionEffect;
 import org.bukkit.potion.PotionEffectType;

 public class GivePickaxe {
   public void allPlayers() {
     ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
     ItemMeta meta = item.getItemMeta();
     meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Config.get().getString("Kazmaİsmi")));
     meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
     meta.addEnchant(Enchantment.DURABILITY, 3, true);
    meta.addEnchant(Enchantment.DIG_SPEED, 4, true);

     
     item.setItemMeta(meta);
     
     for (Player player : Bukkit.getOnlinePlayers()) {
       player.getInventory().addItem(new ItemStack[] { item });
       player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.APPLE, 4) });
                player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COOKED_BEEF, 4) });
       player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
         player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 1));
         player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1));
     } 
   }
 }


