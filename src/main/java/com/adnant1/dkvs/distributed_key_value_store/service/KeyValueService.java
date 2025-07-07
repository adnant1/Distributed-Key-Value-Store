package com.adnant1.dkvs.distributed_key_value_store.service;

import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class KeyValueService {
    private final HashMap<String, String> keyValueStore = new HashMap<>();

    // Store or update a key-value pair
    public void put(String key, String value) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }

        if (value != null && value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be empty.");
        }

        keyValueStore.put(key, value);
    }

    // Retrieve a value by key
    public String get(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }

        return keyValueStore.get(key);
    }

    // Check if a key exists
    public boolean containsKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }

        return keyValueStore.containsKey(key);
    }
}
