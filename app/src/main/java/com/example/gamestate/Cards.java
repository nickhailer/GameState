package com.example.gamestate;

import android.location.GnssAntennaInfo;

import com.example.Card.FighterCard;
import com.example.Card.JudgeCard;
import com.example.Card.SpellCard;

import java.util.ArrayList;
import java.util.Hashtable;

public class Cards {
    // Instance Variables
    public ArrayList<FighterCard> fighterCards = new ArrayList<>(); // Fighter Card Deck
    public ArrayList<SpellCard> spellCards = new ArrayList<SpellCard>();
    public ArrayList<JudgeCard> judgeCards = new ArrayList<JudgeCard>();

    public Cards(int numPlayers){
        // Creating Fighter Cards
        fighterCards.add(new FighterCard("Goblin", numPlayers, 1, 10, false));
        fighterCards.add(new FighterCard("Orc", numPlayers, 2, 8, false));
        fighterCards.add(new FighterCard("Skeleton", numPlayers, 3, 7, true));
        fighterCards.add(new FighterCard("Lizardman", numPlayers, 4, 6, false));
        fighterCards.add(new FighterCard("Ghost", numPlayers, 5, 5, true));
        fighterCards.add(new FighterCard("Succubus", numPlayers, 6, 5, false));
        fighterCards.add(new FighterCard("Dark Elf", numPlayers, 7, 4, false));
        fighterCards.add(new FighterCard("Minotaur", numPlayers, 8, 4, false));
        fighterCards.add(new FighterCard("Demon", numPlayers, 9, 3, false));
        fighterCards.add(new FighterCard("Dragon", numPlayers, 10, 3, false));

        ArrayList<Boolean> faceUp = new ArrayList<Boolean>();
        faceUp.add(true);
        faceUp.add(false);

        // Creating Spell Cards
        for(int i = 0; i < 5; i++) {
            spellCards.add(new SpellCard("Blizzard", faceUp, 4, -6, "", 'd', false));
            spellCards.add(new SpellCard("Might", faceUp, 4, 5, "", 'e', false));
            spellCards.add(new SpellCard("Mana Seal", faceUp, -5, 0, "", 'e', false));
            spellCards.add(new SpellCard("Magic Missile", faceUp, 0, -2, "", 'd', false));
            spellCards.add(new SpellCard("Giant Growth", faceUp, 0, 12, "", 'e', true));
            spellCards.add(new SpellCard("Haste", faceUp, 6, 4, "", 'e', false));
            spellCards.add(new SpellCard("Healing", faceUp, 1, 4, "", 'd', false));
        }

        ArrayList<Character> disallowedSpells = new ArrayList<Character>();
        disallowedSpells.add('s');
        disallowedSpells.add('f');
        disallowedSpells.add('d');

        // Creating Judge Cards
        //JudgeCard(String name, int numPlayers, int manaLimit, String judgementType, ArrayList<Character> disallowedSpells){
        judgeCards.add(new JudgeCard("Adoth", numPlayers, 10, 'e', disallowedSpells));
        judgeCards.add(new JudgeCard("Morla", numPlayers, 12, 'd', disallowedSpells));
        judgeCards.add(new JudgeCard("Orlair", numPlayers, 12, 'e', disallowedSpells));
        judgeCards.add(new JudgeCard("Zapp", numPlayers, 15, 'd', disallowedSpells));
        judgeCards.add(new JudgeCard("Lawty", numPlayers, 10, 'd', disallowedSpells));
        judgeCards.add(new JudgeCard("Lester", numPlayers, 15, 'e', disallowedSpells));

    }

    public Cards(){}
}
