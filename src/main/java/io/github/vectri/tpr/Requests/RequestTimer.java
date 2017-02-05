package io.github.vectri.tpr.Requests;

import io.github.vectri.tpr.TPR;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Handles the timer for requests.
 */
class RequestTimer {
    RequestTimer(TPR plugin, RequestHandler requestHandler, UUID target, UUID requester) {
        new BukkitRunnable() {
            @Override
            public void run() {
                requestHandler.remove(target, requester);
            }
        }.runTaskLaterAsynchronously(plugin, 600);
    }
}
