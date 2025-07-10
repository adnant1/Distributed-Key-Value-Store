package com.adnant1.dkvs.distributed_key_value_store.service;

import java.util.HashMap;
import org.springframework.stereotype.Service;
import com.adnant1.dkvs.distributed_key_value_store.util.ConsistentHashRing;

@Service
public class KeyValueService {
    private final HashMap<String, String> keyValueStore;
    private final ConsistentHashRing hashRing;
    private final String thisNodeId;

    public KeyValueService(ConsistentHashRing hashRing) {
        this.keyValueStore = new HashMap<>();
        this.hashRing = hashRing;
        this.thisNodeId = "nodeA"; // Set to a fixed value for testing
    }

    // Store or update a key-value pair
    public void put(String key, String value) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }

        if (value != null && value.trim().isEmpty()) {
            throw new IllegalArgumentException("Value cannot be empty.");
        }

        String targetNode = hashRing.getNodeForKey(key);

        if (targetNode == thisNodeId) {
            // Store the key-value pair in this node's local store
            keyValueStore.put(key, value);
        } else {
            // Forward the request to the appropriate node (not implemented here)
            // This would typically involve making a network call to the target node
            System.out.println("Stored key '" + key + "' with value '" + value + "' on node: " + targetNode);
        }
    }

    // Retrieve a value by key
    public String get(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }

        String targetNode = hashRing.getNodeForKey(key);

        if (targetNode == thisNodeId) {
            return keyValueStore.get(key);
        } else {
            // Forward the request to the appropriate node (not implemented here)
            System.out.println("Forwarding request to node: " + targetNode);
            return null;
        }
    }

    // Check if a key exists
    public boolean containsKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }

        return keyValueStore.containsKey(key);
    }
}
