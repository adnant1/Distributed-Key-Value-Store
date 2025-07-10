package com.adnant1.dkvs.distributed_key_value_store.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.adnant1.dkvs.distributed_key_value_store.util.ConsistentHashRing;
import com.adnant1.dkvs.distributed_key_value_store.model.KeyValueResult;

@Service
public class CoordinatorService {
    private final KeyValueService keyValueService;
    private final ConsistentHashRing hashRing;
    private final NodeForwardingService nodeForwardingService;
    private final String thisNodeId;

    public CoordinatorService (@Value("${NODE_ID}") String thisNodeId, KeyValueService keyValueService, ConsistentHashRing hashRing, NodeForwardingService nodeForwardingService) {
        this.keyValueService = keyValueService;
        this.hashRing = hashRing;
        this.nodeForwardingService = nodeForwardingService;
        this.thisNodeId = thisNodeId;
    }

    // Coordinator logic to determine where to store or update the key-value pair
    public void put(String key, String value) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }

        if (value != null && value.trim().isEmpty()) {
            throw new IllegalArgumentException("Value cannot be empty.");
        }

        String targetNode = hashRing.getNodeForKey(key);

        if (targetNode.equals(thisNodeId)) {
            keyValueService.put(key, value);
        } else {
            nodeForwardingService.forwardPut(targetNode, key, value);
        }
    }

    // Coordinator logic to determine where to retrieve the value
    public KeyValueResult get(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }

        String targetNode = hashRing.getNodeForKey(key);

        if (targetNode.equals(thisNodeId)) {
            boolean exists = keyValueService.containsKey(key);
            String value = keyValueService.get(key);

            return new KeyValueResult(exists, value);
        } else {
            return nodeForwardingService.forwardGet(targetNode, key);
        }
    }

}
