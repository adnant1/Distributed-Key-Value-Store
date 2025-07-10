package com.adnant1.dkvs.distributed_key_value_store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.adnant1.dkvs.distributed_key_value_store.util.ConsistentHashRing;

/**
 * DEPRECATED: This test suite is obsolete.
 * 
 * KeyValueService no longer handles routing logic - that's now handled by CoordinatorService.
 * Dummy data is used to simulate different nodes in the consistent hash ring.
 * 
 * The dummy data has been replaced with actual data from the Docker nodes.
 */
public class KeyValueServiceIntegrationTest {
    
    private ConsistentHashRing hashRing;
    private KeyValueService keyValueService;

    @BeforeEach
    public void setup() {
        hashRing = new ConsistentHashRing();
        hashRing.addNode("nodeA");
        hashRing.addNode("nodeB");
        hashRing.addNode("nodeC");

        keyValueService = new KeyValueService();
    }

    // Helper method to find a key that maps to the given targetNode
    private String findKeyForNode(String targetNode) {
        for (int i = 0; i < 10000; i++) {
            String candidate = "key" + i;
            String node = hashRing.getNodeForKey(candidate);
            
            if (targetNode.equals(node)) {
                return candidate;
            }
        }
        return null;
    }

    // Helper method to find a key that does not map to the given targetNode
    private String findKeyNotForNode(String targetNode) {
        for (int i = 0; i < 10000; i++) {
            String candidate = "key" + i;
            String node = hashRing.getNodeForKey(candidate);

            if (!targetNode.equals(node)) {
                return candidate;
            }
        }
        return null;
    }

    @Test
    public void testPutWhenLocalIsResponsible() {
        String key = findKeyForNode("nodeA");
        String value = "valueForNodeA";

        keyValueService.put(key, value);

        assertTrue(keyValueService.containsKey(key), "Key should be stored in local node");
    }

    @Test
    public void testPutWhenLocalIsNotResponsible() {
        String key = findKeyNotForNode("nodeA");
        String value = "valueNotForNodeA";

        keyValueService.put(key, value);

        assertFalse(keyValueService.containsKey(key), "Key should not be stored in local node");
    }

    @Test
    public void testGetWhenLocalIsResponsible() {
        String key = findKeyForNode("nodeA");
        String value = "valueForNodeA";

        keyValueService.put(key, value);
        String retrievedValue = keyValueService.get(key);

        assertEquals(value, retrievedValue, "Value should be retrieved from local node");
    }

    @Test
    public void testGetWhenLocalIsNotResponsible() {
        String key = findKeyNotForNode("nodeA");
        String value = "valueNotForNodeA";

        keyValueService.put(key, value);
        String retrievedValue = keyValueService.get(key);

        // For now, the service should return null since the request is forwarded
        assertEquals(null, retrievedValue, "Value should not be retrieved from local node");
    }
}
