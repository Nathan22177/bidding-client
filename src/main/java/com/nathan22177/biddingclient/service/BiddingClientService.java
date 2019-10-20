package com.nathan22177.biddingclient.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BiddingClientService {

    @Value("${server.url}")
    String serverUrl;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public Map<String, String> getKeyTitleMapOfAvailableOpponents(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverUrl + "/getOpponentsMap");
        String response = restTemplate.getForObject(builder.toUriString(), String.class);
        TypeReference<HashMap<String,String>> typeReference = new TypeReference<HashMap<String,String>>() {};
        Map<String, String> keyTitleMapOfAvailableOpponents = new HashMap<>();
        try {
            keyTitleMapOfAvailableOpponents = objectMapper.readValue(response, typeReference);
            log.debug("Successfully fetched list of opponents from the server.");
        } catch (JsonProcessingException exception) {
            log.error("Could not parse response from the server: " + exception);
        }
        return keyTitleMapOfAvailableOpponents;
    }
}
