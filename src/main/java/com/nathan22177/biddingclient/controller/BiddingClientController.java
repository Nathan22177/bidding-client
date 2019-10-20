package com.nathan22177.biddingclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nathan22177.biddingclient.service.BiddingClientService;

@Controller
public class BiddingClientController {

    private final
    BiddingClientService service;

    public BiddingClientController(BiddingClientService service) {
        this.service = service;
    }

    @GetMapping("/play/{opponent}")
    public String startGame(Model model, @PathVariable String opponent) {


        return "game";
    }

    @GetMapping("/menu")
    public String newGame(Model model) {
        model.addAttribute("bots", service.getKeyTitleMapOfAvailableOpponents());
        return "menu";
    }
}
