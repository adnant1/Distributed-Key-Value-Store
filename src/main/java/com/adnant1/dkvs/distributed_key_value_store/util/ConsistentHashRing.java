package com.adnant1.dkvs.distributed_key_value_store.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");

           byte[] digest = md.digest(key.getBytes(StandardCharsets.UTF_8));
           int hash = ((digest[0] & 0xFF) << 24)
                   | ((digest[1] & 0xFF) << 16)
                   | ((digest[2] & 0xFF) << 8)
                   | (digest[3] & 0xFF);

           return hash & 0x7FFFFFFF;
           
       } catch (java.security.NoSuchAlgorithmException e) {
           throw new RuntimeException("MD5 algorithm not found", e);
       }
    }

    public void addNode(String nodeId) {

    }

    public void removeNode(String nodeId) {

    }

    public String getNodeForKey(String key) {

    }

}
