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

        if (value != null) {
            keyValueStore.put(key, value);
        } else {
            keyValueStore.put(key, null);
        }
    }

    // Retrieve a value by key
    public String get(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }

        return keyValueStore.get(key);
    }
}
