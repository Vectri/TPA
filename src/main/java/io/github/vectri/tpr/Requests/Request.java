package io.github.vectri.tpr.Requests;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a request object.
 */
class Request {
    private UUID target;
    private ArrayList<UUID> requesterList = new ArrayList<>();

    Request(UUID target, UUID requester) {
        this.target = target;
        requesterList.add(requester);
    }

    UUID getTarget() {
        return target;
    }

    ArrayList<UUID> getRequesterList() {
        return requesterList;
    }
}
