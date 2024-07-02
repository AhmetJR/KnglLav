package org.ahmetjr.kngllav.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Border implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("lavarisingjr.settings"))
                player.chat("/worldborder set 15 200");
            player.chat("/worldborder set 15 200");
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle("§cAlan Küçülüyor", "§e200 saniye içinde 15 genişiliğide kalacak!.", 10, 70, 20);
                p.playSound(p.getLocation(), org.bukkit.Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0F, 1.0F);
            }
            return false;
        }




        return false;
    }
}
