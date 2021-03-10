package com.example.gamestate;

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

    public FighterCard drawFighterCard(){
        return fighterDeck.remove(randGet.nextInt(fighterDeck.size()));
    }

    public SpellCard drawSpellCard(){
        return spellDeck.remove(randGet.nextInt(spellDeck.size()));
    }

    public JudgeCard drawJudgeCard(){
        return judgeDeck.remove(randGet.nextInt(judgeDeck.size()));
    }

}
