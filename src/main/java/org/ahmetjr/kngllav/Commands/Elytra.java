package org.ahmetjr.kngllav.Commands;

import org.ahmetjr.kngllav.Files.Config;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class Elytra implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            for (Player player2 : Bukkit.getOnlinePlayers()) {
                player2.getInventory().addItem(new ItemStack(Material.ELYTRA, 1));
                player2.getInventory().addItem(new ItemStack(Material.FIREWORK_ROCKET, Config.get().getInt("RocketSayisi")));
            }
        }




        return false;
    }
}
