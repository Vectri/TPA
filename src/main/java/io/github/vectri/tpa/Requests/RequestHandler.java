package io.github.vectri.tpa.Requests;

import io.github.vectri.tpa.TPA;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Handles the data for teleport requests.
 */
public class RequestHandler {
    private TPA plugin;
    private static Map<Player, ArrayList<Player>> teleportRequests = new TreeMap<>(new PlayerComparator());

    public RequestHandler(TPA plugin) {
        this.plugin = plugin;
    }

    public void addRequest(Player target, Player requester) {
        if (!teleportRequests.containsKey(target)) {
            teleportRequests.put(target, new ArrayList<>());
        }
        if (teleportRequests.get(target) != null) {
            plugin.getLogger().info("added");
            teleportRequests.get(target).add(requester);
        }
        new RequestTimer(plugin, this, target, requester);
    }

    void removeRequest(Player target, Player requester) {
        ArrayList requests = teleportRequests.get(target);
        if (requests.contains(requester)) {
            requests.remove(requester);
        }
        if (requests.isEmpty()) {
            teleportRequests.remove(target);
        }
    }

    public ArrayList<Player> getRequests(Player target) {
        if (teleportRequests.containsKey(target)) {
            return teleportRequests.get(target);
        }
        return null;
    }

    public boolean hasRequested(Player requester) {
        for (ArrayList<Player> requests : teleportRequests.values()) {
            if (requests.contains(requester)) {
                return true;
            }
        }
        return false;
    }

    public boolean inRequests(Player target) {
        for (Player player : teleportRequests.keySet()) {
            if (player == target) {
                return true;
            }
        }
        return false;
    }

    private static class PlayerComparator implements Comparator<Player> {
        @Override
        public int compare(Player o1, Player o2) {
            if (o1.getUniqueId() == o2.getUniqueId()) {
                return 1;
            }
            return 0;
        }
    }
}
