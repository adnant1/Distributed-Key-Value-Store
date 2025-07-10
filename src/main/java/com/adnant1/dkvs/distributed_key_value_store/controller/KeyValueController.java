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
import com.adnant1.dkvs.distributed_key_value_store.model.KeyValueRequest;
import com.adnant1.dkvs.distributed_key_value_store.model.KeyValueResult;
import com.adnant1.dkvs.distributed_key_value_store.service.CoordinatorService;

@RestController
public class KeyValueController {
    private final CoordinatorService coordinatorService;

    public KeyValueController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    // Store or update a key-value pair
    @PutMapping("/db")
    public ResponseEntity<String> putKeyValue(@RequestBody KeyValueRequest request) {
        try {
            String key = request.getKey();
            String value = request.getValue();

            coordinatorService.put(key, value);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error storing key-value pair: " + e.getMessage());
        }
    }

    // Retrieve a value by key
    @GetMapping("/db/{key}")
    public ResponseEntity<ItemResponse> getKeyValue(@PathVariable String key) {
        try {
            KeyValueResult result = coordinatorService.get(key);

            if (!result.exists()) {
                // Return an empty item response if the key does not exist
                return ResponseEntity.ok(new ItemResponse(null));
            }

            Map<String, AttributeValue> itemMap;
            String value = result.getValue();

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