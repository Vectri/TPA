package io.github.vectri.tpa.Commands;

import io.github.vectri.tpa.Requests.RequestHandler;
import io.github.vectri.tpa.TPA;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;


/**
 * Handles the requesting of teleports.
 */
public class TPRCommand implements CommandExecutor {
    private TPA plugin;
    private RequestHandler requestHandler;

    public TPRCommand(TPA plugin) {
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
                        if (requestHandler.alreadyRequested(playerUniqueId, senderUniqueId)) {
                            sender.sendMessage("You have already requested to teleport to " + ChatColor.GREEN + player.getName() + ChatColor.RESET + "!");
                        } else {
                            requestHandler.add(playerUniqueId, senderUniqueId);
                            sender.sendMessage("Sent a teleport request to " + player.getName() + ".");
                            player.sendMessage(ChatColor.GREEN + sender.getName() + ChatColor.RESET + " has requested to teleport to you. /tpa "
                                    + ChatColor.GREEN + sender.getName() + ChatColor.RESET + " to accept.");
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
