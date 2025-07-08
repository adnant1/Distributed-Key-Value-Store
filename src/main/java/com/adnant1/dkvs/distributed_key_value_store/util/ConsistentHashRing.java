package com.adnant1.dkvs.distributed_key_value_store.util;

import java.util.Map;
import java.util.TreeMap;

public class ConsistentHashRing {
    private final TreeMap<Integer, String> ring;
    private final Map<String, Integer> nodeToHash;

    public ConsistentHashRing() {
        this.ring = new TreeMap<>();
        this.nodeToHash = new java.util.HashMap<>();
    }

    private int hash(String key) {
       
    }

    public void addNode(String nodeId) {

    }

    public void removeNode(String nodeId) {

    }

    public String getNodeForKey(String key) {

    }

}
