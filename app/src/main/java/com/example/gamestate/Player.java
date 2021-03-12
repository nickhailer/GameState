package com.example.gamestate;

import com.example.Card.SpellCard;

import java.util.ArrayList;

public class Player{

    // Instance Variables
    protected String name;
    protected int coins;
    protected ArrayList<Integer> bets;
    public ArrayList<SpellCard> hand;
    GameState playerPerspective;

    /**
     * Constructor for objects of class Player
     */
    public Player(String name, int coins) {
        this.name = name;
        this.coins = coins;
        this.bets = new ArrayList<Integer>();
        this.hand = new ArrayList<SpellCard>();
    }

}
