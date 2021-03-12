package com.example.gamestate;

import com.example.Card.SpellCard;

import java.util.ArrayList;

public class Player{

    // Instance Variables
    protected String name;
    protected int coins;
    protected ArrayList<Integer> bets;
    public boolean isTheirTurn;
    public ArrayList<SpellCard> hand;
    GameState playerPerspective;


    /**
     * Constructor for objects of class Player
     */
    public Player(String name, int coins, ArrayList<Integer> bets, boolean isTheirTurn, ArrayList<SpellCard> hand) {
        this.name = name;
        this.coins = coins;
        this.bets = bets;
        this.isTheirTurn = isTheirTurn;
        this.hand = hand;
    }

    /**
     * Copy constructor for class Player
     */
    /*
    public Player(Player original) {
        name = original.name;
        isTheirTurn = original.isTheirTurn;
        coins = original.coins;
        bets = original.bets;
        numSpellCards = original.numSpellCards;
    }
*/

}
