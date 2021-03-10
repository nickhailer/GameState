package com.example.gamestate;

public class Player extends GameState{
    // Instance Variables
    protected String name;
    protected int coins;
    protected int bets;
    protected int playerTurn;
    protected int numSpellCards;

    /**
     * Constructor for objects of class Player
     */
    public Player() {
        name = "Player 1";

        // Make it player 1's turn
        playerTurn = 1;

        // Players start with two coins
        coins = 2;

        // Nobody starts with bets
        bets = 0;

        // Players start with 8 spell cards
        numSpellCards = 8;
    }

    /**
     * Copy constructor for class Player
     */
    public Player(Player original) {
        name = original.name;
        playerTurn = original.playerTurn;
        coins = original.coins;
        bets = original.bets;
        numSpellCards = original.numSpellCards;
    }



}
