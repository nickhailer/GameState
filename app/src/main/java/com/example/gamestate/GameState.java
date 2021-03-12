package com.example.gamestate;

import com.example.Card.*;
import java.util.ArrayList;
import java.util.Random;

public class GameState {

    public int roundNum;
    // 1 = player 1, 2 player 2, etc.
    // -1 = setup phase, -2 = judge phase
    public int playerTurn;
    public ArrayList<FighterCard> fighters;
    public JudgeCard judge;
    public ArrayList<Player> players;
    public ArrayList<FighterCard> fighterDeck;
    public ArrayList<SpellCard> spellDeck;
    public ArrayList<JudgeCard> judgeDeck;
    public ArrayList<Card> discardPile;

    private Random randGen = new Random();

    public GameState(int roundNum, int playerTurn, ArrayList<FighterCard> currFighters, JudgeCard currJudge, ArrayList<Player> players,
                     ArrayList<FighterCard> fighterDeck, ArrayList<SpellCard> spellDeck,ArrayList<JudgeCard> judgeDeck,
                     ArrayList<Card> discardPile){
        this.roundNum = roundNum;
        this.playerTurn = playerTurn;
        this.fighters = currFighters;
        this.judge = currJudge;
        this.players = players;
        this.fighterDeck = fighterDeck;
        this.spellDeck = spellDeck;
        this.judgeDeck = judgeDeck;
        this.discardPile = discardPile;
    }

    public GameState(GameState original) {
        roundNum = original.roundNum;
        playerTurn = original.playerTurn;
        fighters = original.fighters;
        judge = original.judge;
        players = original.players;
        fighterDeck = original.fighterDeck;
        spellDeck = original.spellDeck;
        judgeDeck = original.judgeDeck;
        discardPile = original.discardPile;
    }

    public String toString(){
        String s = "";

        //Prints information about the fighters currently in play
        FighterCard f;
        for(int i = 0; i < 5; i ++){
            f = fighters.get(i);
            s += f.name + " has a power of " + f.power + " and has a prize of " + f.prizeMoney + ".\n" +
                    "They also have the following spells attached to them: ";
            for(int j = 0; j < f.spells.size() - 1; j++){
                s += f.spells.get(j).name + ", ";
            }
            s += f.spells.get(f.spells.size() - 1).name + ".\n";
        }

        //Prints information about the judge currently in play
        s += "The judge currently in play is " + judge.name + " and they have a mana limit of " + judge.manaLimit +
                ".\n The spell types they disallow are: ";
        for(int i = 0; i < judge.disallowedSpells.size(); i++){
            if(i == judge.disallowedSpells.size() - 1){
                s += "and ";
            }
            if(judge.disallowedSpells.get(i) == 'd'){
                s += "direct";
            }
            else if(judge.disallowedSpells.get(i) == 'd'){
                s += "enchant";
            }
            else if(judge.disallowedSpells.get(i) == 's'){
                s += "support";
            }
            else if(judge.disallowedSpells.get(i) == 'f'){
                s += "forbidden";
            }
            if(i == judge.disallowedSpells.size() - 1){
                s += ".\n";
            }
            else {
                s += ", ";
            }
        }

        //Prints information about each players hand

        return s;
    }

    //Reveals all face down spell cards on a given player
    public boolean revealCards(FighterCard f, Player p){
        int idx = getPlayerIndex(p);
        if(idx >= 0 && idx < players.size()){
            f.isFaceUp.set(idx, true);
            return true;
        }
        return false;
    }

    //Returns the index of a player in the players list
    public int getPlayerIndex(Player p){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i) == p){
                return i;
            }
        }
        return -1;
    }

    public FighterCard drawFighterCard() { return fighterDeck.remove(randGen.nextInt(fighterDeck.size())); }

    public SpellCard drawSpellCard(){
        return spellDeck.remove(randGen.nextInt(spellDeck.size()));
    }

    public JudgeCard drawJudgeCard(){
        return judgeDeck.remove(randGen.nextInt(judgeDeck.size()));
    }

}
