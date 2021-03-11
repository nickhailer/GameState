package com.example.Card;

import java.util.ArrayList;

public class Card {

    //This is a list of size equal to the number of players because cards are shown as face up or
    //down to different players
    public ArrayList<Boolean> isFaceUp;
    public String name;

    public Card(String name, ArrayList<Boolean> isFaceUp){
        this.name = name;
        this.isFaceUp = isFaceUp;
    }

}