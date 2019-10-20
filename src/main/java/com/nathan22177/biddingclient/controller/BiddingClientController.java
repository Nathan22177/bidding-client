package com.nathan22177.biddingclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.nathan22177.biddingclient.enums.Opponents.botOptions;

@Controller
public class BiddingClientController {

    @GetMapping("/play/{opponent}")
    public String startGame(Model model, @PathVariable String opponent) {


        return "game";
    }

    @GetMapping("/menu")
    public String newGame(Model model) {
        model.addAttribute("bots", botOptions);

        return "menu";
    }
}
