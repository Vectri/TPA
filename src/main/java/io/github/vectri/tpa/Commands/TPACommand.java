package io.github.vectri.tpa.Commands;

import io.github.vectri.tpa.Requests.RequestHandler;
import io.github.vectri.tpa.TPA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Handles the accepting of teleport requests.
 */
public class TPACommand implements CommandExecutor {
    private TPA plugin;
    private RequestHandler requestHandler;

    public TPACommand(TPA plugin) {
        this.plugin = plugin;
        requestHandler = new RequestHandler(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            String target = (args.length != 0) ? args[0].toLowerCase() : null;
            if (target != null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().equalsIgnoreCase(target)) {
                        UUID playerUniqueId = player.getUniqueId();
                        UUID senderUniqueId = ((Player) sender).getUniqueId();
                        if (requestHandler.alreadyRequested(senderUniqueId, playerUniqueId)) {
                            Location senderLocation = ((Player) sender).getLocation();
                            player.teleport(senderLocation);
                            sender.sendMessage("You accepted the teleport request from " + ChatColor.GREEN + player.getName() + ChatColor.RESET + ".");
                            player.sendMessage(ChatColor.GREEN + sender.getName() + ChatColor.RESET + " accepted your teleport request.");
                            requestHandler.remove(senderUniqueId, playerUniqueId);
                        } else {
                            sender.sendMessage(ChatColor.RED + "That player has not requested to teleport to you!");
                        }
                        return true;
                    }
                }
                sender.sendMessage(ChatColor.RED + "That is not the name of a player that is currently online.");
                return true;
            }
        }
        return false; // Returning false shows the correct usage of the command.
    }
}
