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

        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }

        keyValueStore.put(key, value);
    }

}
