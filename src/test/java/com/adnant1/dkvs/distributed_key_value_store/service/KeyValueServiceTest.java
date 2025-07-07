package com.adnant1.dkvs.distributed_key_value_store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class KeyValueServiceTest {

    private final KeyValueService keyValueService = new KeyValueService();
    
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
