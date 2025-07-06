package com.adnant1.dkvs.distributed_key_value_store.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adnant1.dkvs.distributed_key_value_store.model.AttributeValue;
import com.adnant1.dkvs.distributed_key_value_store.model.ItemResponse;
import com.adnant1.dkvs.distributed_key_value_store.service.KeyValueService;

@RestController
public class KeyValueController {
    private final KeyValueService keyValueService;

    public KeyValueController(KeyValueService keyValueService) {
        this.keyValueService = keyValueService;
    }

    // Store or update a key-value pair
    @PutMapping("/dkvs/{key}")
    public ResponseEntity<String> putKeyValue(@PathVariable String key, @RequestBody String value) {
        try {
            keyValueService.put(key, value);
            return ResponseEntity.ok("Key-value pair stored successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error storing key-value pair: " + e.getMessage());
    }
    }

    // Retrieve a value by key
    @GetMapping("/dkvs/{key}")
    public ResponseEntity<ItemResponse> getKeyValue(@PathVariable String key) {
        try {
            if (!keyValueService.containsKey(key)) {
                return ResponseEntity.ok(new ItemResponse(null));
            }

            String value = keyValueService.get(key);

            Map<String, AttributeValue> itemMap;
            if (value != null) {
                itemMap = Map.of(
                    "key", new AttributeValue(key),
                    "value", new AttributeValue(value)
                );
            } else {
            itemMap = Map.of(
                "key", new AttributeValue(key)
                // no value attribute
            );
        }

        return ResponseEntity.ok(new ItemResponse(itemMap));
        
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}