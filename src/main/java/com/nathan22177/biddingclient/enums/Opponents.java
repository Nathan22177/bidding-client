package com.nathan22177.biddingclient.enums;

public enum Opponents {
    /***
     * Bids it's opponent's last bid plus one if has advantage, else skips round.
     * */
    COPYCAT_BOT,

    /***
     * Always bids mean price of 2 QU's.
     * */
    FAIR_BOT,

    /***
     * lehaSVV2009s AwesomeBidder strategy refactored and appropriated.
     * Comments remain true to the source.
     * */
    LEHASVV2009_BOT,

    /***
     * My own strategy.
     * */
    NATHAN22177_BOT,

    /***
     * PyramidPlayers AdvancedBidder strategy refactored and appropriated.
     * Comments remain true to the source.
     * */
    PYRAMID_PLAYER_BOT,

    /***
     * Gradually raises bid so that would go with empty balance at the end.
     * */
    RISING_BOT,

    /***
     * Waits for an advantage then bids median plus 2.
     * */
    SAFE_BOT,

    /***
     * Waits for an advantage then bids last winning bid plus one;
     * */
    WIN_INC_BOT
}
