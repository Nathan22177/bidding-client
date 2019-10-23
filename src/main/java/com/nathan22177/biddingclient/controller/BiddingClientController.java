package com.nathan22177.biddingclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public BiddingClientController(BiddingClientService service) {
        this.service = service;
    }

    @GetMapping("/play/{opponent}")
    public String startGame(Model model, @PathVariable String opponent) {
        if (opponent.contains("RANDOM")) {
            opponent = getRandomOpponent();
        }
        model.addAttribute("opponent", service.getAvailableOpponents().get(opponent));
        model.addAttribute("conditions", service.createNewGameAgainstTheBot(opponent));
        return "game";
    }

    private String getRandomOpponent() {
        return service.getAvailableOpponents().keySet()
                .stream()
                .filter(key -> !key.equalsIgnoreCase("RANDOM"))
                .collect(CollectorUtils.toShuffledStream())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There are no bots available. Something is wrong."));
    }

    @GetMapping("/checkIfServerIsUp")
    private ResponseEntity checkIfServerIsUp(Model model) {
        Boolean serverIsUp = service.isServerUp();
        return ResponseEntity.ok(serverIsUp);
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        Boolean serverIsUp = service.isServerUp();
        model.addAttribute("serverIsUp", serverIsUp);
        model.addAttribute("bots", service.getAvailableOpponents());
        return "menu";
    }
}
