package com.example.Card;

import java.util.ArrayList;

public class SpellCard extends Card {

    public int mana;
    public int powerMod;
    //if this is empty show the powerMod instead
    public String cardText;
    //d = direct, e = enchant, s = support
    public char spellType;
    public boolean isForbidden;
    //f = fighter, j = judge, p = player, s = spell
    public char targetType;

    public SpellCard(String name, ArrayList<Boolean> isFaceUp, int mana, int powerMod,
                     String cardText, char spellType, boolean isForbidden){
        //adds attributes of spell cards
        super(name, isFaceUp);
        this.mana = mana;
        this.powerMod = powerMod;
        this.cardText = cardText;
        this.spellType = spellType;
        this.isForbidden = isForbidden;
        this.targetType = 'f';
    }

    public SpellCard(String name, ArrayList<Boolean> isFaceUp, int mana, int powerMod,
                     String cardText, char spellType, boolean isForbidden, char targetType){
        super(name, isFaceUp);
        this.mana = mana;
        this.powerMod = powerMod;
        this.cardText = cardText;
        this.spellType = spellType;
        this.isForbidden = isForbidden;
        this.targetType = targetType;
    }

}