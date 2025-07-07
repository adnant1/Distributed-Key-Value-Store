package com.adnant1.dkvs.distributed_key_value_store.controller;

import com.adnant1.dkvs.distributed_key_value_store.service.KeyValueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class KeyValueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KeyValueService keyValueService;

    // Test PUT with valid key and value
    @Test
    void testPutWithValidInput() throws Exception {
        mockMvc.perform(put("/db")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"name\", \"value\":\"Alex\"}"))
                .andExpect(status().isOk());
    }

    // Test PUT with null value (key-only item)
    @Test
    void testPutWithNullValue() throws Exception {
        mockMvc.perform(put("/db")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"name\"}"))
                .andExpect(status().isOk());
    }

    // Test PUT with empty string value
    @Test
    void testPutWithEmptyValue() throws Exception {
        mockMvc.perform(put("/db")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"name\", \"value\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    // Test PUT with empty key
    @Test
    void testPutWithEmptyKey() throws Exception {
        mockMvc.perform(put("/db")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"\", \"value\":\"Alex\"}"))
                .andExpect(status().isBadRequest());
    }

    // Test PUT with null key
    @Test
    void testPutWithNullKey() throws Exception {
        mockMvc.perform(put("/db")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\":\"Alex\"}")) // key field omitted
                .andExpect(status().isBadRequest());
    }

    // Test GET with existing key and value
    @Test
    void testGetWithExistingKey() throws Exception {
        String key = "name";
        String value = "Alex";
        keyValueService.put(key, value);

        mockMvc.perform(get("/db/{key}", key))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Item.key.S").value(key))
                .andExpect(jsonPath("$.Item.value.S").value(value));
    }

    // Test GET with existing key but no value
    @Test
    void testGetWithKeyOnlyItem() throws Exception {
        String key = "user123";
        keyValueService.put(key, null);

        mockMvc.perform(get("/db/{key}", key))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Item.key.S").value(key))
                .andExpect(jsonPath("$.Item.value").doesNotExist());
    }

    // Test GET with non-existing key
    @Test
    void testGetWithNonExistingKey() throws Exception {
        String key = "nonexistent";

        mockMvc.perform(get("/db/{key}", key))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Item").value((Object) null));
    }

    // Test GET with empty key
    @Test
    void testGetWithEmptyKey() throws Exception {
        mockMvc.perform(get("/db/"))
                .andExpect(status().isNotFound());
    }

    // Test GET with null key
    @Test
    void testGetWithNullKey() throws Exception {
        mockMvc.perform(get("/db/"))
                .andExpect(status().isNotFound());
    }
}
