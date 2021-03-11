package com.example.gamestate;

import android.location.GnssAntennaInfo;

public class Cards {
    // Instance Variables
    protected String[] fighterCards; // Fighter Card Deck
    protected String[] judgeCards;   // Judge Card Deck
    protected String[] dsCards;      // Direct Spell Card Deck
    protected String[] esCards;      // Enchanted Spell Card Deck
    protected String[] ssCards;      // Support Spell Card Deck
    protected String[] fsCards;      // Forbidden Spell Card Deck

    public Cards(){
        fighterCards = new String[] {"Goblin", "Orc", "Skeleton", "Lizardman", "Ghost",
                                     "Succubus", "Dark Elf", "Minotaur", "Demon", "Dragon"};

        judgeCards = new String[] {"Adoth", "Tad", "Ferine", "Morla",
                                    "Orlair", "Lawty", "Lester", "Zapp"};

        dsCards = new String[] {"Blizzard", " Missile Magic", "Healing", "Lightning Bolt"};

        esCards = new String[] {"Might", "Mana Seal", "Power Awakening", "Cause Unpopularity",
                                "Haste", "Mana Boost"};

        ssCards = new String[] {"Duplicate", "Dispel Magic", "Invisibility", "Amnesia", "Dimension Door"};

        fsCards = new String[] {"Metamorphosis", "Shrink", "Giant Growth", "Reflection",
                                "Alteration", "Anti-Magic Field"};
    }
}
