package com.adnant1.dkvs.distributed_key_value_store.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adnant1.dkvs.distributed_key_value_store.model.KeyValueRequest;
import com.adnant1.dkvs.distributed_key_value_store.model.KeyValueResult;

@Service
public class NodeForwardingService {
    private final RestTemplate restTemplate;

    public NodeForwardingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Forward a PUT request to the target node
    public void forwardPut(String targetNode, String key, String value) {
        String url = String.format("http://%s:8080/db", targetNode);

        try {
            KeyValueRequest request = new KeyValueRequest(key, value);
            restTemplate.put(url, request);

        } catch (Exception e) {
            throw new RuntimeException("Error forwarding PUT request to node " + targetNode, e);
        }
    }

	public KeyValueResult forwardGet(String targetNode, String key) {
        String url = String.format("http://%s:8080/db/%s", targetNode, key);

        try {
            return restTemplate.getForObject(url, KeyValueResult.class);

        } catch (Exception e) {
            throw new RuntimeException("Error forwarding GET request to node " + targetNode, e);
        }
	}
    
}
