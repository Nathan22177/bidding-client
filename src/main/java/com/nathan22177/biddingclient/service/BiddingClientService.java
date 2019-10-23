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
import com.nathan22177.biddingclient.repository.VersusBotRepository;
import com.nathan22177.collection.BiddingRound;
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

    @Autowired
    VersusBotRepository repository;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public Map<String, Opponent> getAvailableOpponents() {
        return Opponent.botOptions.stream().collect(Collectors.toMap(Opponent::getName, Function.identity()));
    }

    public Long createNewGameAgainstTheBot(String opponent) {
        PlayerVersusBotGame game = NewGameUtil.createNewGameAgainstTheBot(new BidderPlayer(), Opponent.valueOf(opponent));
        repository.saveAndFlush(game);
        return game.getId();
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

    public PlayerVersusBotGame loadVersusBotGame(Long gameId) {
        return repository.getOne(gameId);
    }

    public BiddingRound placeBidVsBot(Long gameId, Integer bid) {
        PlayerVersusBotGame game = repository.getOne(gameId);
        game.playerPlacesBid(bid);
        return game.getBluePlayer().getBiddingHistory().peekLast();
    }
}
