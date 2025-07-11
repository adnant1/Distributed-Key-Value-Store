package com.adnant1.dkvs.distributed_key_value_store.util;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class ConsistentHashRing {
    private static final int REPLICATION_FACTOR = 3;
    private final TreeMap<Integer, String> ring;
    private final HashMap<String, Integer> nodeToHash;

    public ConsistentHashRing() {
        this.ring = new TreeMap<>();
        this.nodeToHash = new HashMap<>();
    }

    public ConsistentHashRing(String[] initialNodes) {
        this();
        if (initialNodes != null) {
            for (String node : initialNodes) {
                addNode(node);
            }
        }
    }

    // Generates a hash for the given key using MD5 algorithm
    int hash(String key) {
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");

           // Convert the key to bytes and compute the digest
           byte[] digest = md.digest(key.getBytes(StandardCharsets.UTF_8));

           // Use the first 4 bytes of the digest to create a 32-bit integer
           int hash = ((digest[0] & 0xFF) << 24)
                   | ((digest[1] & 0xFF) << 16)
                   | ((digest[2] & 0xFF) << 8)
                   | (digest[3] & 0xFF);

            // Ensure the hash is non-negative
           return hash & 0x7FFFFFFF;

       } catch (java.security.NoSuchAlgorithmException e) {
           throw new RuntimeException("MD5 algorithm not found", e);
       }
    }

    public ArrayList<String> getReplicasForKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        ArrayList<String> replicas = new ArrayList<>();
        Integer currentHash = hash(key);
        Integer primaryHash = ring.ceilingKey(currentHash);
        if (primaryHash == null) {
            primaryHash = ring.firstKey();
        }

        String primaryNode = ring.get(primaryHash);
        replicas.add(primaryNode);
        currentHash = primaryHash;

        for (int i = 1; i < REPLICATION_FACTOR; i++) {
            Integer nextHash = ring.higherKey(currentHash);
            if (nextHash == null) {
                nextHash = ring.firstKey();
            }

            String nextNode = ring.get(nextHash);
            if (!replicas.contains(nextNode)) {
                replicas.add(nextNode);
            }
            currentHash = nextHash;
        }
        
        return replicas;
    }

    // Adds a node to the consistent hash ring
    public void addNode(String nodeId) {
        if (nodeId == null || nodeId.isEmpty()) {
            throw new IllegalArgumentException("Node ID cannot be null or empty");
        }

        int nodeHash = hash(nodeId);
        if (nodeToHash.containsKey(nodeId)) {
            throw new IllegalArgumentException("Node ID already exists in the ring");
        }

        nodeToHash.put(nodeId, nodeHash);
        ring.put(nodeHash, nodeId);
    }

    // Removes a node from the consistent hash ring
    public void removeNode(String nodeId) {
        if (nodeId == null || nodeId.isEmpty()) {
            throw new IllegalArgumentException("Node ID cannot be null or empty");
        }

        Integer nodeHash = nodeToHash.remove(nodeId);
        if (nodeHash == null) {
            throw new IllegalArgumentException("Node ID does not exist in the ring");
        }

        ring.remove(nodeHash);
    }

    // Retrieves the node responsible for the given key
    public String getNodeForKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }

        if (ring.isEmpty()) {
            throw new IllegalStateException("No nodes available in the ring");
        }

        int keyHash = hash(key);
        Integer nodeHash = ring.ceilingKey(keyHash);

        if (nodeHash == null) {
            nodeHash = ring.firstKey();
        }

        return ring.get(nodeHash);
    }
}
