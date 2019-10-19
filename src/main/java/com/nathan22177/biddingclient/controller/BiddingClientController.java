package com.nathan22177.biddingclient.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("day-ahead")
public class BiddingClientController {

    @GetMapping("/play/{opponent}")
    public String startGame(Model model, @PathVariable String opponent) {


        return "game";
    }

    @GetMapping("/menu")
    public String newGame(Model model, @PathVariable String opponent) {


        return "menu";
    }
}
