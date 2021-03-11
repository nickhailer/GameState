package com.example.Card;

import java.util.ArrayList;

public class JudgeCard extends Card {

    public int manaLimit;
    public String judgementType;
    public ArrayList<Character> disallowedSpells;

    public JudgeCard(String name, int numPlayers, int manaLimit, String judgementType, ArrayList<Character> disallowedSpells){
        super(name, new ArrayList<Boolean>() {{
            for(int i = 0; i < numPlayers; i++){
                add(true);
            }
        }});

    }

}