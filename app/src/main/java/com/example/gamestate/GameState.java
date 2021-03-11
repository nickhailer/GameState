package com.example.gamestate;

import com.example.Card.*;
import java.util.ArrayList;
import java.util.Random;

public class GameState {

    public ArrayList<FighterCard> currFighters;
    public JudgeCard currJudge;
    public ArrayList<Player> players;
    public ArrayList<FighterCard> fighterDeck;
    public ArrayList<SpellCard> spellDeck;
    public ArrayList<JudgeCard> judgeDeck;
    public ArrayList<Card> discardPile;

    private Random randGen = new Random();

    public GameState(){

    }

    public FighterCard drawFighterCard() { return fighterDeck.remove(randGen.nextInt(fighterDeck.size())); }

    public SpellCard drawSpellCard(){
        return spellDeck.remove(randGen.nextInt(spellDeck.size()));
    }

    public JudgeCard drawJudgeCard(){
        return judgeDeck.remove(randGen.nextInt(judgeDeck.size()));
    }

}
