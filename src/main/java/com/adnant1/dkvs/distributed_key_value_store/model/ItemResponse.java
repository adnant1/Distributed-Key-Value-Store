package com.adnant1.dkvs.distributed_key_value_store.model;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemResponse {
    @JsonProperty("Item")
    private Map<String, AttributeValue> item;

    public ItemResponse() {}

    public ItemResponse(Map<String, AttributeValue> item) {
        this.item = item;
    }

    public Map<String, AttributeValue> getItem() {
        return item;
    }

    public void setItem(Map<String, AttributeValue> item) {
        this.item = item;
    }
}
