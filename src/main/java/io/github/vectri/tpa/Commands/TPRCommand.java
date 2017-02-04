package io.github.vectri.tpa.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


/**
 * Handles the requesting of teleports.
 */
public class TPRCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            String target = (args.length != 0) ? args[0].toLowerCase() : null;
            if (target != null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().equalsIgnoreCase(target)) {
                        sender.sendMessage("Found your player!");
                        return true;
                    }
                }
            }
        }
        return false; // Returning false shows the correct usage of the command.
    }
}
