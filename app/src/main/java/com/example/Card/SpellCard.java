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

    public SpellCard(String name, ArrayList<Boolean> isFaceUp, int mana, int powerMod, String cardText,
                     char spellType, boolean isForbidden){
        super(name, isFaceUp);
        this.mana = mana;
        this.powerMod = powerMod;
        this.cardText = cardText;
        this.spellType = spellType;
        this.isForbidden = isForbidden;
    }

}