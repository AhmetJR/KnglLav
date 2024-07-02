package org.ahmetjr.kngllav.Commands;

import org.ahmetjr.kngllav.LavaInfo;
import org.ahmetjr.kngllav.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ActionOff implements CommandExecutor {
    private final Main plugin;

    public ActionOff(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();

            if (plugin.getPlayerTasks().containsKey(playerUUID)) {
                plugin.getPlayerTasks().get(playerUUID).cancel();
                plugin.getPlayerTasks().remove(playerUUID);
                player.sendMessage(ChatColor.RED + "Action bar mesajı kapatıldı!");
            } else {
                player.sendMessage(ChatColor.RED + "Action bar mesajı zaten aktif değil.");
            }

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Bu komut yalnızca oyuncular tarafından kullanılabilir.");
            return false;
        }
    }
}


