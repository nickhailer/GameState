package com.example.gamestate;

import com.example.Card.*;
import java.util.ArrayList;
import java.util.Random;

public class GameState {

    public int roundNum;
    // 1 = player 1, 2 player 2, etc.
    // -1 = setup phase, -2 = judge phase
    public int playerTurn;
    public ArrayList<FighterCard> currFighters;
    public JudgeCard currJudge;
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
        this.currFighters = currFighters;
        this.currJudge = currJudge;
        this.players = players;
        this.fighterDeck = fighterDeck;
        this.spellDeck = spellDeck;
        this.judgeDeck = judgeDeck;
        this.discardPile = discardPile;
    }

    public GameState(GameState original) {
        roundNum = original.roundNum;
        playerTurn = original.playerTurn;
        currFighters = original.currFighters;
        currJudge = original.currJudge;
        players = original.players;
        fighterDeck = original.fighterDeck;
        spellDeck = original.spellDeck;
        judgeDeck = original.judgeDeck;
        discardPile = original.discardPile;
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
