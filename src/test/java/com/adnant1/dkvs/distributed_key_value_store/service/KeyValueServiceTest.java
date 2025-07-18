package com.adnant1.dkvs.distributed_key_value_store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.adnant1.dkvs.distributed_key_value_store.util.ConsistentHashRing;

/**
 * DEPRECATED: This test suite is obsolete.
 * 
 * KeyValueService no longer handles routing logic - that's now handled by CoordinatorService.
 * This service now only handles local storage operations.
 * 
 * These tests verify the basic local storage functionality but don't test the
 * real application flow.
 */
@Disabled
class KeyValueServiceTest {

    private KeyValueService keyValueService;
    private ConsistentHashRing hashRing;

    @BeforeEach
    void setUp() {
        hashRing = mock(ConsistentHashRing.class);
        keyValueService = new KeyValueService();

        when(hashRing.getNodeForKey(anyString())).thenReturn("nodeA");
    }
    
    @Test
    void testPutWithValidKeyAndValue() {
        String key = "name";
        String value = "Alex";

        keyValueService.put(key, value);
        String retrievedValue = keyValueService.get(key);

        assertEquals(value, retrievedValue);
    }

    @Test
    void testPutWithNullKey() {
        String key = null;
        String value = "Alex";

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            keyValueService.put(key, value);
        });
        assertEquals("Key cannot be null or empty.", e.getMessage());
    }

    @Test
    void testPutWithEmptyKey() {
        String key = "";
        String value = "Alex";

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            keyValueService.put(key, value);
        });
        assertEquals("Key cannot be null or empty.", e.getMessage());
    }

    @Test
    void testPutWithNullValue() {
        String key = "name";
        String value = null;

        keyValueService.put(key, value);
        String retrievedValue = keyValueService.get(key);

        assertEquals(null, retrievedValue);
    }

    @Test
    void testGetWithExistingKey() {
        String key = "name";
        String value = "Alex";

        keyValueService.put(key, value);
        String retrievedValue = keyValueService.get(key);

        assertEquals(value, retrievedValue);
    }

    @Test
    void testGetWithNonExistingKey() {
        String key = "name";
        String value = "Alex";

        keyValueService.put(key, value);
        String retrievedValue = keyValueService.get("nonExistingKey");

        assertEquals(null, retrievedValue);
    }

    @Test
    void testGetWithNullKey() {
        String key = null;

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            keyValueService.get(key);
        });
        assertEquals("Key cannot be null or empty.", e.getMessage());
    }

    @Test
    void testGetWithEmptyKey() {
        String key = "";

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            keyValueService.get(key);
        });
        assertEquals("Key cannot be null or empty.", e.getMessage());
    }
}
