package io.github.vectri.tpr.Commands;

import io.github.vectri.tpr.Requests.RequestHandler;
import io.github.vectri.tpr.TPR;
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
    private TPR plugin;
    private RequestHandler requestHandler;

    public TPRCommand(TPR plugin) {
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
                    addRequest(target, (Player) sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "That is not the name of a player that is currently online.");
                }
                return true;
            }
        }
        return false; // Returning false shows the correct usage of the command.
    }

    private void addRequest(Player target, Player requester) {
        UUID targetUniqueId = target.getUniqueId();
        UUID requesterUniqueId = requester.getUniqueId();
        if (requesterUniqueId == targetUniqueId) {
            requester.sendMessage(ChatColor.RED + "You cannot request to teleport to yourself!");
            return;
        }
        if (requestHandler.alreadyRequested(targetUniqueId, requesterUniqueId)) {
            requester.sendMessage("You have already requested to teleport to " + ChatColor.GREEN + target.getName() + ChatColor.RESET + "!");
            return;
        }
        requestHandler.add(targetUniqueId, requesterUniqueId);
        requester.sendMessage("Sent a teleport request to " + ChatColor.GREEN + target.getName() + ChatColor.RESET + ".");
        target.sendMessage(ChatColor.GREEN + requester.getName() + ChatColor.RESET + " has requested to teleport to you. ");
        target.sendMessage("/tpa " + ChatColor.GREEN + requester.getName() + ChatColor.RESET + " to accept.");
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
