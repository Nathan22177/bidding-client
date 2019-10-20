package com.nathan22177.biddingclient.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Opponents {
    /***
     * Another user playing trough web interface
     * */
    ANOTHER_PLAYER("Real human"),

    /***
     * Bids it's opponent's last bid plus one if has advantage, else skips round.
     * */
    COPYCAT_BOT("Copycaster"),

    /***
     * Always bids mean price of 2 QU's.
     * */
    FAIR_BOT("Lawful_Goodman"),

    /***
     * lehaSVV2009s AwesomeBidder strategy refactored and appropriated.
     * Comments remain true to the source.
     * */
    LEHASVV2009_BOT("LehaSVV2009"),

    /***
     * My own strategy.
     * */
    NATHAN22177_BOT("Nathan22177"),

    /***
     * PyramidPlayers AdvancedBidder strategy refactored and appropriated.
     * Comments remain true to the source.
     * */
    PYRAMID_PLAYER_BOT("Pyramid_Player"),

    /***
     * Gradually raises bid so that would go with empty balance at the end.
     * */
    RISING_BOT("Riser"),

    /***
     * Waits for an advantage then bids median plus 2.
     * */
    SAFE_BOT("The Calm One"),

    /***
     * Waits for an advantage then bids last winning bid plus one;
     * */
    WIN_INC_BOT("Chad"),

    /***
     * Random strategy
     * */
    RANDOM_BOT("RANDOM");

    public final String title;
    public final String name;


    public static final List<Opponents> botOptions = Arrays.stream(Opponents.values())
            .filter(opponent -> opponent != ANOTHER_PLAYER)
            .collect(Collectors.toList());

    Opponents(String title) {
        this.title = title;
        this.name = this.toString();
    }
}
