package com.adnant1.dkvs.distributed_key_value_store.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConsistentHashRingTest {
    
    private ConsistentHashRing ring;

    @BeforeEach
    public void setUp() {
        ring = new ConsistentHashRing();
    }

    @Test
    public void testAddSingleNode() {
        String node = "NodeA";
        ring.addNode(node);
        
        String mappedNode = ring.getNodeForKey("someKey");

        assertEquals("NodeA", mappedNode);
    }

    @Test
    public void testAddMultipleNodes() {
        String nodeA = "NodeA";
        String nodeB = "NodeB";
        String nodeC = "NodeC";

        ring.addNode(nodeA);
        ring.addNode(nodeB);
        ring.addNode(nodeC);

        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";
        
        String mappedNode1 = ring.getNodeForKey(key1);
        String mappedNode2 = ring.getNodeForKey(key2);
        String mappedNode3 = ring.getNodeForKey(key3);
        
        assertEquals(true, 
            (mappedNode1.equals(nodeA) || mappedNode1.equals(nodeB) || mappedNode1.equals(nodeC)) &&
            (mappedNode2.equals(nodeA) || mappedNode2.equals(nodeB) || mappedNode2.equals(nodeC)) &&
            (mappedNode3.equals(nodeA) || mappedNode3.equals(nodeB) || mappedNode3.equals(nodeC))
        );
    }
    
    @Test
    public void testRemoveNode() {
        String nodeA = "NodeA";
        String nodeB = "NodeB";

        ring.addNode(nodeA);
        ring.addNode(nodeB);

        ring.removeNode(nodeA);

        String mappedNodeForKey = ring.getNodeForKey("someKey");
        
        assertEquals("NodeB", mappedNodeForKey);
    }

    @Test
    public void testRemoveNonExistentNode() {
        String NodeA = "NodeA";
        String NodeB = "NodeB";

        ring.addNode(NodeA);
        
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> ring.removeNode(NodeB),
            "Node ID does not exist in the ring"
        );

        assertEquals("Node ID does not exist in the ring", e.getMessage());
    }

    @Test
    public void testAddNullNode() {
        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> ring.addNode(null),
            "Node ID cannot be null or empty"
        );

        assertEquals("Node ID cannot be null or empty", e.getMessage());
    }

    @Test
    public void testGetNodeForKeyOnEmptyRing() {
        IllegalStateException e = assertThrows(
            IllegalStateException.class,
            () -> ring.getNodeForKey("someKey"),
            "No nodes available in the ring"
        );

        assertEquals("No nodes available in the ring", e.getMessage());
    }

    @Test
    public void testWrapAroundKey() {
        ring = spy(new ConsistentHashRing());
        ring.addNode("NodeA");
        ring.addNode("NodeB");
        ring.addNode("NodeC");

        doReturn(Integer.MAX_VALUE).when(ring).hash("wrapAroundKey");

        String mappedNode = ring.getNodeForKey("wrapAroundKey");

        assertEquals("NodeA", mappedNode);
    }

    @Test
    public void testAddDuplicateNode() {
        String nodeA = "NodeA";

        ring.addNode(nodeA);

        IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> ring.addNode(nodeA),
            "Node ID already exists in the ring"
        );

        assertEquals("Node ID already exists in the ring", e.getMessage());
    }
}
