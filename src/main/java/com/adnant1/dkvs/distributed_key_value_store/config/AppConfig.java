package com.adnant1.dkvs.distributed_key_value_store.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.adnant1.dkvs.distributed_key_value_store.util.ConsistentHashRing;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ConsistentHashRing consistentHashRing(@Value("${NODE_IDS:defaultNode1, defaultNode2}") String nodeIds) {
        String[] initialNodes = nodeIds.split(",");
        return new ConsistentHashRing(initialNodes);
    }
}
