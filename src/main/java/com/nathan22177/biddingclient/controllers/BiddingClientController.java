package com.nathan22177.biddingclient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nathan22177.biddingclient.service.BiddingClientService;
import com.nathan22177.biddingclient.utils.CollectorUtils;
import com.nathan22177.collection.BiddingRound;
import com.nathan22177.enums.Status;
import com.nathan22177.game.PlayerVersusBotGame;

@Controller
public class BiddingClientController {

    @Autowired
    private final BiddingClientService  service;

    public BiddingClientController(BiddingClientService service) {
        this.service = service;
    }

    @GetMapping("/play_vs_bot/{opponent}")
    public String startVersusBotGame(Model model, @PathVariable String opponent) {
        if (opponent.contains("RANDOM")) {
            opponent = getRandomOpponent();
        }
        model.addAttribute("opponent", service.getAvailableOpponents().get(opponent));
        Long gameId = service.createNewGameAgainstTheBot(opponent);
        model.addAttribute("gameId", gameId);
        return "redirect:/vs_bot/" + gameId;
    }

    @GetMapping("/vs_bot/{gameId}")
    public String loadVersusBotGame(Model model, @PathVariable Long gameId) {
        PlayerVersusBotGame game = service.loadVersusBotGame(gameId);
        model.addAttribute("game", game);
        model.addAttribute("playerCanPlaceBids",game.getStatus() == Status.WAITING_FOR_BIDS);
        return "vs_bot_interface";
    }

    @PostMapping("/vs_bot/{gameId}/{bid}")
    public BiddingRound placeBidVersusBot(@PathVariable Long gameId, @PathVariable Integer bid) {
        return service.placeBidVersusBot(gameId, bid);
    }

    private String getRandomOpponent() {
        return service.getAvailableOpponents().keySet()
                .stream()
                .filter(key -> !key.equalsIgnoreCase("RANDOM"))
                .collect(CollectorUtils.toShuffledStream())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There are no bots available. Something is wrong."));
    }

    @GetMapping("/check_if_server_is_up")
    private ResponseEntity checkIfServerIsUp(Model model) {
        Boolean serverIsUp = service.isServerUp();
        return ResponseEntity.ok(serverIsUp);
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        Boolean serverIsUp = service.isServerUp();
        model.addAttribute("serverIsUp", serverIsUp);
        model.addAttribute("bots", service.getAvailableOpponents());
        model.addAttribute("listOfGamesVersusBots", service.getStartedGamesVersusBots());
        return "menu";
    }
}
