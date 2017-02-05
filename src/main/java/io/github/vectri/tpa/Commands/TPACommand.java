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
            String arg0 = (args.length != 0) ? args[0].toLowerCase() : null;
            if (arg0 != null) {
                Player target = matchPlayerName(arg0);
                if (target != null) {
                    acceptRequest(target, (Player) sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "That is not the name of a player that is currently online.");
                }
                return true;
            }
        }
        return false; // Returning false shows the correct usage of the command.
    }

    private void acceptRequest(Player target, Player requester) {
        UUID targetUniqueId = target.getUniqueId();
        UUID requesterUniqueId = requester.getUniqueId();
        if (requestHandler.alreadyRequested(requesterUniqueId, targetUniqueId)) {
            Location senderLocation = requester.getLocation();
            target.teleport(senderLocation);
            requester.sendMessage("You accepted the teleport request from " + ChatColor.GREEN + target.getName() + ChatColor.RESET + ".");
            target.sendMessage(ChatColor.GREEN + requester.getName() + ChatColor.RESET + " accepted your teleport request.");
            requestHandler.remove(requesterUniqueId, targetUniqueId);
        } else {
            requester.sendMessage(ChatColor.RED + "That player has not requested to teleport to you!");
        }
    }

    private Player matchPlayerName(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }
}
