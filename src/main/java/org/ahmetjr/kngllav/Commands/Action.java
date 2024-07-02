package org.ahmetjr.kngllav.Commands;

import org.ahmetjr.kngllav.LavaInfo;
import org.ahmetjr.kngllav.Main;
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

public class Action implements CommandExecutor {

    private Main main;
    LavaInfo lavaInfo = Main.instance.getLavaInfo();
    Location edgeMax = lavaInfo.topLeft;
    private final Main plugin;

    public Action(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();

            // Zaten bir görev varsa, önce iptal et
            if (plugin.getPlayerTasks().containsKey(playerUUID)) {
                plugin.getPlayerTasks().get(playerUUID).cancel();
            }

            player.sendMessage(ChatColor.GREEN + "Action bar mesajı gösteriliyor!");

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    int lavyukseklik = edgeMax.getBlockY()-1;
                    player.sendActionBar( ChatColor.BOLD + "" + ChatColor.YELLOW + "Lav Yüksekliği: " + lavyukseklik + " blok");
                }
            }.runTaskTimer(plugin, 0, 20); // Her 1 saniyede bir yenile

            plugin.getPlayerTasks().put(playerUUID, task);
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Bu komut yalnızca oyuncular tarafından kullanılabilir.");
            return false;
        }
    }
}


