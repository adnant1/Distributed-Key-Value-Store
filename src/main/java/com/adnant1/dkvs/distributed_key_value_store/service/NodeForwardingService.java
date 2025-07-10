package com.adnant1.dkvs.distributed_key_value_store.service;

import org.springframework.stereotype.Service;
import com.adnant1.dkvs.distributed_key_value_store.model.KeyValueResult;

@Service
public class NodeForwardingService {

    public void forwardPut(String targetNode, String key, String value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'forwardPut'");
    }

	public KeyValueResult forwardGet(String targetNode, String key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'forwardGet'");
	}
    
}
