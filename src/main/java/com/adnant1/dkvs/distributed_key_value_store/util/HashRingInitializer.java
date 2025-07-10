package com.adnant1.dkvs.distributed_key_value_store.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class HashRingInitializer {

    @Autowired
    private ConsistentHashRing hashRing;

    @PostConstruct
    public void init() {
        // Initialize the hash ring with some nodes
        String[] initialNodes = {"nodeA", "nodeB", "nodeC"};
        
        for (String node : initialNodes) {
            hashRing.addNode(node);
        }
        
        System.out.println("Hash ring initialized with nodes: " + initialNodes.length);
    }
    
}
