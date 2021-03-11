package com.example.Card;

import java.util.ArrayList;

public class FighterCard extends Card {

    public int power;
    public int prizeMoney;
    public boolean isUndead;
    public ArrayList<SpellCard> spells;

    public FighterCard(String name, int numPlayers, int power, int prizeMoney, boolean isUndead){
        //Code from https://stackoverflow.com/a/16195084
        super(name, new ArrayList<Boolean>() {{
            for(int i = 0; i < numPlayers; i++){
                add(true);
            }
        }});
        this.power = power;
        this.prizeMoney = prizeMoney;
        this.isUndead = isUndead;
        //spells is constructed empty
        spells = new ArrayList<>();
    }

    //maybe make this return a boolean if successful?
    public void attachSpell(SpellCard spell){ spells.add(spell); }

}