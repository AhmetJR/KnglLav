package org.ahmetjr.kngllav.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Settings implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission("lavarisingjr.settings"))
                player.chat("/gamerule doFireTick false");
                player.chat("/whitelist off");
                player.sendMessage("§aOyun ayarları başarıyla yapıldı.");
                player.sendMessage("§aServer Tüm Oyunculara Açıldı");
                return false;
        }


        return false;
    }
}
