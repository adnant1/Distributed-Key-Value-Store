package com.adnant1.dkvs.distributed_key_value_store.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AttributeValue {
    @JsonIgnore
    private String s;

    public AttributeValue() {}

    public AttributeValue(String s) {
        this.s = s;
    }

    @JsonProperty("S")
    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
