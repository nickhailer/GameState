package com.example.gamestate;

import com.example.Card.*;
import java.util.ArrayList;
import java.util.Random;

public class GameState {

    public int roundNum;
    // 1 = player 1, 2 = player 2, etc.
    // -1 = setup phase, -2 = judge phase
    public int playerTurn;
    // 1 = betting, 2 = spell playing
    public int phase;
    public ArrayList<FighterCard> currFighters;
    public JudgeCard currJudge;
    public ArrayList<Player> players;
    public ArrayList<FighterCard> fighterDeck;
    public ArrayList<SpellCard> spellDeck;
    public ArrayList<JudgeCard> judgeDeck;
    public ArrayList<Card> discardPile;

    private Random randGen = new Random();
    public Player player;

    public GameState(int roundNum, int playerTurn, int phase, ArrayList<FighterCard> currFighters, JudgeCard currJudge, ArrayList<Player> players,
                     ArrayList<FighterCard> fighterDeck, ArrayList<SpellCard> spellDeck,ArrayList<JudgeCard> judgeDeck,
                     ArrayList<Card> discardPile){
        this.roundNum = roundNum;
        this.playerTurn = playerTurn;
        this.phase = phase;
        this.currFighters = currFighters;
        this.currJudge = currJudge;
        this.players = players;
        this.fighterDeck = fighterDeck;
        this.spellDeck = spellDeck;
        this.judgeDeck = judgeDeck;
        this.discardPile = discardPile;
    }


    public GameState(String name, boolean isTheirTurn, int coins, ArrayList<Integer> bets, ArrayList<SpellCard> hand) {
        name = player.name;
        isTheirTurn = player.isTheirTurn;
        coins = player.coins;
        bets = player.bets;
        hand = player.hand;
    }

    public FighterCard drawFighterCard() { return fighterDeck.remove(randGen.nextInt(fighterDeck.size())); }

    public SpellCard drawSpellCard(){
        return spellDeck.remove(randGen.nextInt(spellDeck.size()));
    }

    public JudgeCard drawJudgeCard(){
        return judgeDeck.remove(randGen.nextInt(judgeDeck.size()));
    }

}
