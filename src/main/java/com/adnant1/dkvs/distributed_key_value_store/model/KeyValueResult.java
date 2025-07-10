package com.adnant1.dkvs.distributed_key_value_store.model;

public class KeyValueResult {
    private boolean exists;
    private String value;

    public KeyValueResult(boolean exists, String value) {
        this.exists = exists;
        this.value = value;
    }

    public boolean exists() {
        return exists;
    }

    public String getValue() {
        return value;
    }
    
}
