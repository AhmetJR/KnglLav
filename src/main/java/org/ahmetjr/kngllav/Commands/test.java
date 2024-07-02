package org.ahmetjr.kngllav.Commands;

import org.ahmetjr.kngllav.LavaInfo;
import org.ahmetjr.kngllav.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class test implements CommandExecutor {

    LavaInfo lavaInfo = Main.instance.getLavaInfo();

    Location edgeMax = lavaInfo.topLeft;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            int test = edgeMax.getBlockY();
            player.sendMessage(String.valueOf(test));



    }
        return false;
    }

}
