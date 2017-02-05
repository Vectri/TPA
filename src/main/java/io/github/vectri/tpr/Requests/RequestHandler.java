package io.github.vectri.tpr.Requests;

import io.github.vectri.tpr.TPR;

import java.util.*;

/**
 * Handles the data for teleport requests.
 */
public class RequestHandler {
    private TPR plugin;
    private static ArrayList<Request> requestArrayList = new ArrayList<>();

    public RequestHandler(TPR plugin) {
        this.plugin = plugin;
    }

    private Request get(UUID target) {
        for (Request request : requestArrayList) {
            if (request.getTarget() == target) {
                return request;
            }
        }
        return null;
    }

    public void add(UUID target, UUID requester) {
        Request request = get(target);
        if (request == null) {
            requestArrayList.add(new Request(target, requester));
            new RequestTimer(plugin, this, target, requester);
            return;
        }
        ArrayList<UUID> requesterList = request.getRequesterList();
        requesterList.add(requester);
    }

    public boolean remove(UUID target, UUID requester) {
        Request request = get(target);
        if (request != null) {
            ArrayList<UUID> requesterList = request.getRequesterList();
            requesterList.remove(requester);
            if (requesterList.isEmpty()) {
                requestArrayList.remove(request);
            }
            return true;
        }
        return false;
    }

    public boolean alreadyRequested(UUID target, UUID requester) {
        Request request = get(target);
        if (request != null) {
            ArrayList<UUID> requesterList = request.getRequesterList();
            if (requesterList.contains(requester)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<UUID> getRequests(UUID target) {
        Request request = get(target);
        if (request != null) {
            return request.getRequesterList();
        }
        return null;
    }
}
