package io.github.vectri.tpa.Requests;

import io.github.vectri.tpa.TPA;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Handles the timer for requests.
 */
class RequestTimer {
    private TPA plugin;
    private RequestHandler requestHandler;

    RequestTimer(TPA plugin, RequestHandler requestHandler, UUID target, UUID requester) {
        this.plugin = plugin;
        this.requestHandler = requestHandler;

        new BukkitRunnable() {
            @Override
            public void run() {
                requestHandler.remove(target, requester);
                plugin.getLogger().info("Deleted request for " + requester + " to teleport to " + target + ".");
            }
        }.runTaskLaterAsynchronously(plugin, 600);
    }
}
