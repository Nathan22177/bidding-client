package com.nathan22177.biddingclient.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nathan22177.biddingclient.service.BiddingClientService;
import com.nathan22177.biddingclient.utils.CollectorUtils;

@Controller
public class BiddingClientController {

    @Autowired
    private final BiddingClientService  service;

    private final Map<String, String> availableOpponents;

    public BiddingClientController(BiddingClientService service) {
        this.service = service;
        this.availableOpponents = service.getKeyTitleMapOfAvailableOpponents();
    }

    @GetMapping("/play/{opponent}")
    public String startGame(Model model, @PathVariable String opponent) {
        if (opponent.contains("RANDOM")) {
            opponent = getRandomOpponent();
        }
        model.addAttribute("opponent", availableOpponents.get(opponent));
        model.addAttribute("conditions", service.startNewGame(opponent));
        return "game";
    }

    private String getRandomOpponent() {
        return service.getKeyTitleMapOfAvailableOpponents().keySet()
                .stream()
                .filter(key -> !key.equalsIgnoreCase("RANDOM"))
                .collect(CollectorUtils.toShuffledStream())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There are no bots available. Something is wrong."));
    }

    @GetMapping("/menu")
    public String newGame(Model model) {
        model.addAttribute("bots", availableOpponents);
        return "menu";
    }
}
