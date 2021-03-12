package com.example.gamestate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

import com.example.Card.*;
import java.util.ArrayList;
import java.util.Hashtable;
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
                     ArrayList<FighterCard> fighterDeck, ArrayList<SpellCard> spellDeck, ArrayList<JudgeCard> judgeDeck,
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

    @Override
    public String toString() {
        String cFighters = "";
        String p = "";
        String fighterD = "";
        String spellD = "";
        String judgeD = "";
        String dPile = "";

        for(int i=0; i<currFighters.size(); i++) {
            cFighters = cFighters + ", " + currFighters.get(i);
        }
        for(int i=0; i<players.size(); i++) {
            p = p + ", " + players.get(i);
        }
        for(int i=0; i<fighterDeck.size(); i++) {
            fighterD = fighterD + ", " + fighterDeck.get(i);
        }
        for(int i=0; i<spellDeck.size(); i++) {
            spellD = spellD + ", " + spellDeck.get(i);
        }
        for(int i=0; i<judgeDeck.size(); i++) {
            judgeD = judgeD + ", " + judgeDeck.get(i);
        }
        for(int i=0; i<discardPile.size(); i++) {
            dPile = dPile + ", " + discardPile.get(i);
        }

        return "Round: " + roundNum +
                "\nPlayer's " + playerTurn + " turn " +
                "\nCurrent Fighters: " + cFighters +
                "\nPlayers: " + p +
                "\nFighter Deck: " + fighterD +
                "\nSpell Deck: " + spellD +
                "\nJudge Deck: " + judgeD +
                "\nDiscard Pile: " + dPile;

    }

    public boolean placeBet(Player player) {
        if(player.isTheirTurn && player.bets.size() <= 3) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean playSpellCard(Player player, SpellCard sc) {
        if(player.isTheirTurn && player.hand.contains(sc)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean pass(Player player) {
        if(player.isTheirTurn) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean discardCards(Player player) {
        // If it's players turn and its during the setup phase
        // then they can discard cards
        if(player.isTheirTurn && playerTurn == -1) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean detectMagic(Player player) {
        if(player.isTheirTurn && player.hand.size() > 0 ) {
            return true;
        }
        else {
            return false;
        }
    }

    public FighterCard drawFighterCard() { return fighterDeck.remove(randGen.nextInt(fighterDeck.size())); }

    public SpellCard drawSpellCard(){ return spellDeck.remove(randGen.nextInt(spellDeck.size())); }

    public JudgeCard drawJudgeCard(){
        return judgeDeck.remove(randGen.nextInt(judgeDeck.size()));
    }

}
