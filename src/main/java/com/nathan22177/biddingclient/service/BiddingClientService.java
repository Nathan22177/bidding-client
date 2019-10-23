package com.nathan22177.biddingclient.service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nathan22177.bidder.BidderPlayer;
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

    public PlayerVersusBotGame createNewGameAgainstTheBot(String opponent) {
        return NewGameUtil.createNewGameAgainstTheBot(new BidderPlayer(), Opponent.valueOf(opponent));
    }

    public boolean isServerUp() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverUrl + "/getServerStatus");
        Boolean serverIsUp;
        try {
            ResponseEntity response = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, String.class);
            serverIsUp = response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException exception) {
            log.error("The server is down: " + exception);
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
