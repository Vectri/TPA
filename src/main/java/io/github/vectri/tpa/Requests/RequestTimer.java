package io.github.vectri.tpa.Requests;

import io.github.vectri.tpa.TPA;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Handles the timer for requests.
 */
class RequestTimer {
    RequestTimer(TPA plugin, RequestHandler requestHandler, UUID target, UUID requester) {
        new BukkitRunnable() {
            @Override
            public void run() {
                requestHandler.remove(target, requester);
            }
        }.runTaskLaterAsynchronously(plugin, 600);
    }
}
