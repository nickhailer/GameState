package com.example.gamestate;

import android.text.BoringLayout;

import com.example.Card.*;
import java.util.ArrayList;
import java.util.Random;

public class GameState {

    public int roundNum;
    // 1 = player 1, 2 player 2, etc.
    // -1 = start of a round, -2 = end of a round
    public int playerTurn;
    public ArrayList<FighterCard> fighters;
    public JudgeCard judge;
    private ArrayList<Player> players;
    private ArrayList<FighterCard> fighterDeck;
    private ArrayList<SpellCard> spellDeck;
    private ArrayList<JudgeCard> judgeDeck;
    public ArrayList<Card> discardPile;
    public Cards decks;

    private Random randGen = new Random();

    public GameState(int roundNum, int playerTurn, ArrayList<FighterCard> currFighters, JudgeCard currJudge, ArrayList<Player> players,
                     ArrayList<Card> discardPile){
        //general attributes stored by the game
        this.roundNum = roundNum;
        this.playerTurn = playerTurn;
        this.fighters = currFighters;
        this.judge = currJudge;
        this.players = players;
        this.decks = new Cards(players.size());
        this.discardPile = discardPile;
    }

    //idx is which player you want to create this for
    public GameState(GameState original, int idx) {

        //Copies non object fields
        roundNum = original.roundNum;
        playerTurn = original.playerTurn;

        //Initializes the fighters array
        fighters = new ArrayList<>();

        //Temp helper variables
        FighterCard origFighter;
        FighterCard newFighter;
        SpellCard attSpell;

        //Iterates through each fighter in the original fighters
        for(int i = 0; i < original.fighters.size(); i++){

            origFighter = original.fighters.get(i);

            //Initializes the new fighter
            newFighter = new FighterCard(origFighter.name, original.players.size(), origFighter.power,
                    origFighter.prizeMoney, origFighter.isUndead);

            //Iterates through each spell on the original fighter
            for(int j = 0; j < origFighter.spells.size(); j++){
                attSpell = origFighter.spells.get(i);

                //Attaches face up spells regularly
                if(attSpell.isFaceUp.get(idx)){
                    newFighter.spells.add(new SpellCard(attSpell.name, new ArrayList<Boolean>() {{ add(true); }},
                            attSpell.mana, attSpell.powerMod, attSpell.cardText, attSpell.spellType,
                            attSpell.isForbidden));
                }

                //Attaches face down spells with a placeholder obj
                else{
                    newFighter.spells.add(new SpellCard("Unknown", new ArrayList<Boolean>() {{ add(false); }},
                            0, 0, "", ' ', false));
                }
            }

            //Adds the newly created fighter
            fighters.add(newFighter);
        }

        //Initializes the new judge
        judge = new JudgeCard(original.judge.name, original.players.size(), original.judge.manaLimit,
                original.judge.judgementType, new ArrayList<>());
        //Copies over the disallowedSpells array to the new judge
        for(int i = 0; i < original.judge.disallowedSpells.size(); i++){
            judge.disallowedSpells.add(original.judge.disallowedSpells.get(i));
        }

        //Initializes the players array
        players = new ArrayList<>();

        //Temp helper variables
        Player origPlayer;
        Player newPlayer;
        SpellCard origSpell;

        //Iterates through list of players
        for(int i = 0; i < original.players.size(); i++){

            //Gets the original player
            origPlayer = original.players.get(i);

            //If it's your player add them normally
            if(i == idx){

                //Initializes your player
                newPlayer = new Player(origPlayer.name, origPlayer.coins, new ArrayList<>(),
                        new ArrayList<>());

                //Adds your player's bets
                for(int j = 0; j < origPlayer.bets.size(); j++) {
                    newPlayer.bets.add(origPlayer.bets.get(j));
                }

                //Adds your player's spell cards to their hand
                for(int j = 0; j < origPlayer.hand.size(); i++){
                    origSpell = origPlayer.hand.get(i);
                    newPlayer.hand.add(new SpellCard(origSpell.name, new ArrayList<Boolean>() {{ add(true); }},
                            origSpell.mana, origSpell.powerMod, origSpell.cardText, origSpell.spellType,
                            origSpell.isForbidden));
                }
            }

            //Otherwise add them with limited information
            else{

                //Initializes the new player
                newPlayer = new Player(origPlayer.name, origPlayer.coins, new ArrayList<>(), new ArrayList<>());

                //Adds the player's bets
                for(int j = 0; j < origPlayer.bets.size(); j++) {
                    newPlayer.bets.add(-1);
                }

                //Adds the player's spell cards to their hand
                for(int j = 0; j < origPlayer.hand.size(); j++){
                    newPlayer.hand.add(new SpellCard("Unknown", new ArrayList<Boolean>() {{ add(false); }},
                            0, 0, "", ' ', false));
                }
            }

            //Adds your player
            players.add(i, newPlayer);
        }

        //Initializes the decks
        fighterDeck = new ArrayList<>();
        spellDeck = new ArrayList<>();
        judgeDeck = new ArrayList<>();

        //Adds place holder cards to each deck
        for(int i = 0; i < original.fighterDeck.size(); i++){
            fighterDeck.add(new FighterCard("Unknown", 0, 0, 0,
                    false));
        }
        for(int i = 0; i < original.spellDeck.size(); i++){
            spellDeck.add(new SpellCard("Unknown", new ArrayList<Boolean>() {{ add(false); }},
                    0, 0, "", ' ', false));
        }
        for(int i = 0; i < original.judgeDeck.size(); i++){
            judgeDeck.add(new JudgeCard("Unknown", 0, 0, ' ',
                    new ArrayList<>()));
        }

        //Initializes discard pile
        discardPile = new ArrayList<>();

        //Iterates through each card in the original discard pile
        Card discardedCard;
        for(int i = 0; i < original.discardPile.size(); i++){

            //gets original card
            discardedCard = original.discardPile.get(i);

            //Determines which type of card to correctly add it
            if(discardedCard instanceof FighterCard){
                discardPile.add(new FighterCard(discardedCard.name, original.players.size(),
                        ((FighterCard) discardedCard).power, ((FighterCard) discardedCard).prizeMoney,
                        ((FighterCard) discardedCard).isUndead));
            }
            else if(discardedCard instanceof SpellCard){
                discardPile.add(new SpellCard(discardedCard.name, new ArrayList<Boolean>(){{ add(true); }},
                        ((SpellCard) discardedCard).mana, ((SpellCard) discardedCard).powerMod,
                        ((SpellCard) discardedCard).cardText, ((SpellCard) discardedCard).spellType,
                        ((SpellCard) discardedCard).isForbidden));
            }
            else if(discardedCard instanceof JudgeCard){
                discardPile.add(new JudgeCard(discardedCard.name, original.players.size(),
                        ((JudgeCard) discardedCard).manaLimit, ((JudgeCard) discardedCard).judgementType,
                        new ArrayList<Character>()));
                //Copies the characters from the disallowedSpells array
                for(int j = 0; j < ((JudgeCard) discardedCard).disallowedSpells.size(); j++){
                    ((JudgeCard) discardPile.get(i)).disallowedSpells.add(((JudgeCard) discardedCard).disallowedSpells.get(j));
                }
            }
        }

    }

    public String toString(){
        String s = "";

        //Prints the current round of the game
        s += "It is round " + roundNum + " and ";
        if(playerTurn == -1){
            s += "the players are currently placing their bets.\n";
        }
        else if(playerTurn == -2){
            s += "the players are currently deciding which cards to discard.\n";
        }
        else{
            s += "it is currently " + players.get(playerTurn).name + "'s turn";
        }
        s += "\n";

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
        s += "\n";

        //Prints information about the judge currently in play
        s += "The judge currently in play is " + judge.name + ", they have a mana limit of " + judge.manaLimit +
                ", and their judgement type is ";
        if(judge.judgementType == 'd'){
            s += "dispel";
        }
        else if(judge.judgementType == 'e'){
            s += "eject";
        }
        s += ".\n The spell types they disallow are: ";
        for(int i = 0; i < judge.disallowedSpells.size(); i++){
            if(i == judge.disallowedSpells.size() - 1){
                s += "and ";
            }
            if(judge.disallowedSpells.get(i) == 'd'){
                s += "direct";
            }
            else if(judge.disallowedSpells.get(i) == 'e'){
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
        s += "\n";

        //Prints information about each player
        Player p;
        for(int i = 0; i < players.size(); i++){
            p = players.get(i);
            s += p.name + " has " + p.coins + " coins and bet on the ";
            for(int j = 0; j < p.bets.size() - 1; j++){
                s += fighters.get(j).name + ", ";
            }
            s += "and " + fighters.get(p.bets.size() - 1).name + ".\n";
            s += "In their hand they have the following spells: ";
            for(int j = 0; j < p.hand.size() - 1; j++){
                s += p.hand.get(j).name + ", ";
            }
            s += "and " + p.hand.get(p.hand.size() - 1).name + ".\n";
        }
        s += "\n";

        //Prints the cards in the discard pile
        s += "The discard pile has the following cards: ";
        for(int i = 0; i < discardPile.size() - 1; i++){
            s += discardPile.get(i).name + ", ";
        }
        s += "and " + discardPile.get(discardPile.size() - 1).name + ".\n";

        return s;
    }

    //Checks bets and places them
    public boolean placeBet(int idx, Player player) {
        if(idx == playerTurn && player.bets.size() <= 3) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean playSpellCard(int idx, Player player, SpellCard sc) {
        if(idx == playerTurn && player.hand.contains(sc)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean pass(int idx) {
        if(idx == playerTurn) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean discardCards(int idx) {
        // If it's players turn and its during the setup phase
        // then they can discard cards
        if(idx == playerTurn && playerTurn == -1) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean detectMagic(int idx, Player player) {
        if(idx == playerTurn && player.hand.size() > 0 ) {
            return true;
        }
        else {
            return false;
        }
    }

    //Reveals all face down spell cards on a given player
    public void revealCards(FighterCard f, int idx){
        for(int i = 0; i < f.spells.size(); i++){
            f.spells.get(i).isFaceUp.set(idx, true);
        }
    }

    public FighterCard drawFighterCard() { return fighterDeck.remove(randGen.nextInt(fighterDeck.size())); }

    public SpellCard drawSpellCard(){
        return spellDeck.remove(randGen.nextInt(spellDeck.size()));
    }

    public JudgeCard drawJudgeCard(){
        return judgeDeck.remove(randGen.nextInt(judgeDeck.size()));
    }

}
