package com.nathan22177.biddingclient.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nathan22177.bidder.BidderPlayer;
import com.nathan22177.bidder.player.BidderPlayer;
import com.nathan22177.enums.Opponent;
import com.nathan22177.game.PlayerVersusBotGame;
import com.nathan22177.util.NewGameUtil;

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



    public Map<String, Opponent> getAvailableOpponents() {
        return Opponent.botOptions.stream().collect(Collectors.toMap(Opponent::getName, Function.identity()));
    }

    public Conditions newGameAgainstTheBot(String opponent) {

        return new Conditions(conditions);
    }

    public PlayerVersusBotGame startNewGameAgainstTheBot(BidderPlayer bluePlayer, Opponent opponent) {
        return NewGameUtil.createNewGameAgainstTheBot(bluePlayer, opponent)
    }


    public boolean isServerUp() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverUrl + "/getServerStatus");
        Boolean serverIsUp;
        try {
            String response = restTemplate.getForObject(builder.toUriString(), String.class);
            TypeReference<Boolean> typeReference = new TypeReference<Boolean>() {};
            serverIsUp = objectMapper.readValue(response, typeReference);
            log.debug("The server is operational.");
        } catch (RestClientException exception) {
            log.error("The server is down: " + exception);
            serverIsUp = Boolean.FALSE;
        } catch (Exception exception) {
            log.error("Could not parse response from the server: " + exception);
            serverIsUp = Boolean.FALSE;
        }
        return serverIsUp;
    }

    private static class Conditions {
        // used in thymeleaf
        public int initialCash;
        public int initialQuantity;

        private Conditions(Map<String, Integer> conditions) {
            this.initialCash = conditions.get("initialCash");
            this.initialQuantity = conditions.get("initialQuantity");
        }
    }
}
