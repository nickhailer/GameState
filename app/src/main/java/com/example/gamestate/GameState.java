package com.example.gamestate;

import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import com.example.Card.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameState {

    public int roundNum;
    // 1 = player 1, 2 player 2, etc.
    // -1 = start of a round, -2 = end of a round
    public int playerTurn;
    public ArrayList<FighterCard> fighters = new ArrayList<FighterCard>();
    public JudgeCard judge;
    public ArrayList<Player> players;
    public ArrayList<Card> discardPile;
    public Cards decks;

    private int discardCounter;
    private int passCounter;
    private Random randGen = new Random();

    public GameState(ArrayList<Player> players){
        this.decks = new Cards(players.size());
        this.roundNum = 0;
        while(this.fighters.size() < 5) {
            FighterCard fCards= drawFighterCard();
            this.fighters.add(fCards);
        }
        this.playerTurn = -1;
        this.players = players;
        this.discardPile = new ArrayList<Card>();
        this.setupPhase();
        this.passCounter = 0;
        this.discardCounter = 0;
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
                original.judge.judgementType, new ArrayList<Character>());
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
                newPlayer = new Player(origPlayer.name, origPlayer.coins);

                //Adds your player's bets
                for(int j = 0; j < origPlayer.bets.size(); j++) {
                    newPlayer.bets.add(origPlayer.bets.get(j));
                }

                //Adds your player's spell cards to their hand
                for(int j = 0; j < origPlayer.hand.size(); j++){
                    origSpell = origPlayer.hand.get(j);
                    newPlayer.hand.add(new SpellCard(origSpell.name, new ArrayList<Boolean>() {{ add(true); }},
                            origSpell.mana, origSpell.powerMod, origSpell.cardText, origSpell.spellType,
                            origSpell.isForbidden, origSpell.targetType));
                }
            }

            //Otherwise add them with limited information
            else{

                //Initializes the new player
                newPlayer = new Player(origPlayer.name, origPlayer.coins);

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
        decks = new Cards();

        //Adds place holder cards to each deck
        for(int i = 0; i < original.decks.fighterCards.size(); i++){
            decks.fighterCards.add(new FighterCard("Unknown", 0, 0, 0,
                    false));
        }
        for(int i = 0; i < original.decks.spellCards.size(); i++){
            decks.spellCards.add(new SpellCard("Unknown", new ArrayList<Boolean>() {{ add(false); }},
                    0, 0, "", ' ', false));
        }
        for(int i = 0; i < original.decks.judgeCards.size(); i++){
            decks.judgeCards.add(new JudgeCard("Unknown", 0, 0, ' ',
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
                        ((SpellCard) discardedCard).isForbidden, ((SpellCard) discardedCard).targetType));
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

    @Override
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
            s += "it is currently " + players.get(playerTurn).name + "'s turn.\n";
        }

        //Prints information about the fighters currently in play
        FighterCard f;
        for(int i = 0; i < 5; i++){
            f = fighters.get(i);
            s += f.name + " has a power of " + f.power + " and has a prize of " + f.prizeMoney + ".\n" +
                    "They also have the following spells attached to them: ";
            if(f.spells.size() == 0){
                s += "Nothing.\n";
            }
            for(int j = 0; j < f.spells.size(); j++){
                s += f.spells.get(j).name;
                if(j == f.spells.size() - 1){
                    s += ".\n";
                }
                else{
                    s += ", ";
                }
            }
        }

        //Prints information about the judge currently in play
        s += "The judge currently in play is " + judge.name + ", they have a mana limit of " + judge.manaLimit +
                ", and their judgement type is ";
        if(judge.judgementType == 'd'){
            s += "dispel";
        }
        else if(judge.judgementType == 'e'){
            s += "eject";
        }
        s += ".\nThe spell types they disallow are: ";
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

        //Prints information about each player
        Player p;
        for(int i = 0; i < players.size(); i++){
            p = players.get(i);
            s += p.name + " has " + p.coins + " coins and bet on the ";
            if(p.bets.size() == 0){
                s += "Nothing.\n";
            }
            for(int j = 0; j < p.bets.size(); j++){
                s += fighters.get(p.bets.get(j)).name;
                if(j == p.bets.size() - 1){
                    s += ".\n";
                }
                else{
                    s += ", ";
                }
            }

            s += "In their hand they have the following spells: ";
            if(p.hand.size() == 0){
                s += "Nothing.\n";
            }
            for(int j = 0; j < p.hand.size(); j++){
                s += p.hand.get(j).name;
                if(j == p.hand.size() - 1){
                    s += ".\n";
                }
                else{
                    s += ", ";
                }
            }
        }

        //Prints the cards in the discard pile
        s += "The discard pile has the following cards: ";
        if(discardPile.size() == 0){
            s += "Nothing.\n";
        }
        for(int i = 0; i < discardPile.size(); i++){
            s += discardPile.get(i).name;
            if(i == discardPile.size() - 1){
                s += ".\n";
            }
            else{
                s += ", ";
            }
        }

        return s;
    }

    public boolean placeBets(int idx, ArrayList<Integer> bets) {
        if(playerTurn != -1) {
            return false;
        }
        players.get(idx).bets = bets;
        //Checks if all players have placed their bets
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).bets.size() == 0){
                return true;
            }
        }
        //If all players have placed their bets go the first players turn
        playerTurn = 0;
        return true;
    }

    //0 = judge, 1 - 5 = fighters, 6+ = players, (1+)0(1-5) = spells played on fighter
    //ex. 203 = second spell on the third fighter
    public boolean playSpellCard(int idx, int spell, int target) {
        //Check if its this players turn
        if(idx != playerTurn) {
            return false;
        }
        //Checks if you have the spell in your hand
        if(spell >= players.get(idx).hand.size() || spell < 0){
            return false;
        }
        //Checks if target is valid
        if(players.get(idx).hand.get(spell).targetType == 'f' ){
            if(target < 1 || target > 5){
                return false;
            }
        }
        else if(players.get(idx).hand.get(spell).targetType == 'j'){
            if(target != 0) {
                return false;
            }
        }
        else if(players.get(idx).hand.get(spell).targetType == 'p'){
            if(target < 6) {
                return false;
            }
        }
        else if(players.get(idx).hand.get(spell).targetType == 's'){
            if(target < 100 || target % 100 < 1 || target % 100 > 5) {
                return false;
            }
        }
        //Attaches card to the target
        if(target >= 1 && target <= 5){
            fighters.get(target - 1).spells.add(players.get(idx).hand.get(spell));
        }
        //Removes card from your hand
        discardPile.add(players.get(idx).hand.remove(spell));
        //Moves turn to the next player
        playerTurn = (playerTurn + 1) % players.size();
        return true;
    }

    public boolean discardCards(int idx, ArrayList<Integer> cards) {
        // If it's players turn and its during the setup phase
        // then they can discard cards
        if(playerTurn != -2) {
            return false;
        }
        //Checks if you have the cards to discard
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i) < 0 || cards.get(i) > players.get(idx).hand.size()){
                return false;
            }
        }
        //Removes the cards from your hand and place them in the discard pile
        //Code from https://stackoverflow.com/a/4950905
        Collections.sort(cards, Collections.reverseOrder());
        for(int c : cards) {
            discardPile.add(players.get(idx).hand.get(c));
            players.get(idx).hand.remove(c);
        }
        //If every player has discarded go onto next phase
        discardCounter++;
        if(discardCounter == players.size()){
            discardPile.add(judge);
            setupPhase();
            discardCounter = 0;
            playerTurn = -1;
        }
        return true;
    }

    //Sets the game for the another rounds
    public void setupPhase(){

        //Fills each players hand
        int handSize;
        if(players.size() == 6){
            handSize = 5;
        }
        if(players.size() == 5){
            handSize = 6;
        }
        else{
            handSize = 8;
        }
        for(int i = 0; i < players.size(); i++){
            while(players.get(i).hand.size() < handSize){
                players.get(i).hand.add(drawSpellCard());

            }
        }

        //Recycles fighter cards from discard pile
        for(int i = 0; i < discardPile.size(); i++){
            if(discardPile.get(i) instanceof FighterCard){
                decks.fighterCards.add((FighterCard) discardPile.remove(i));
            }
        }

        //Gets a new judge
        judge = drawJudgeCard();

    }

    public boolean pass(int idx) {
        if(idx != playerTurn) {
            return false;
        }
        passCounter++;
        if(passCounter == players.size()){
            passCounter = 0;
            combatPhase();
            playerTurn = -2;
            return true;
        }
        playerTurn = (playerTurn + 1) % players.size();
        return true;
    }

    //simulates combat between the fighters
    public void combatPhase(){

        //Checks the mana on each fighter
        int totalMana;
        for(int i = 0; i < fighters.size(); i++){
            totalMana = 0;
            for(int j = 0; j < fighters.get(i).spells.size(); j++){
                totalMana += fighters.get(i).spells.get(j).mana;
                //Applies the judge's judgement
                if(totalMana > judge.manaLimit){
                    if(judge.judgementType == 'd'){
                        fighters.get(i).spells = new ArrayList<>();
                    }
                    //Ejects the fighter by replacing them with a filler card and discarding them
                    else if(judge.judgementType == 'e'){
                        discardPile.add(fighters.get(i));
                        fighters.set(i, new FighterCard("Empty", 0, -99,
                                0, false));
                    }
                }
            }
        }

        //Finds the fighter with the highest power
        int winningFighter = 0;
        int highestPower = 0;
        for(int i = 0; i < fighters.size(); i++){
            if(highestPower < fighters.get(i).calcPower()){
                highestPower = fighters.get(i).calcPower();
                winningFighter = i;
            }
            else if(highestPower == fighters.get(i).calcPower()){
                if(fighters.get(i).power > fighters.get(winningFighter).power){
                    winningFighter = i;
                }
            }
        }

        //Awards coins to the players with winning bets
        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < players.get(i).bets.size(); j++){
                if(players.get(i).bets.get(j) == winningFighter){
                    if(players.get(i).bets.size() == 1){
                        players.get(i).coins += fighters.get(winningFighter).prizeMoney * 2;
                    }
                    else if(players.get(i).bets.size() == 2){
                        players.get(i).coins += fighters.get(winningFighter).prizeMoney;
                    }
                    else if(players.get(i).bets.size() == 3){
                        players.get(i).coins += Math.ceil((double)fighters.get(winningFighter).prizeMoney / 2);
                    }
                }
            }
        }

        //Removes fighters from the battlefield
        for(int i = fighters.size() - 1; i >= 0; i--){
            discardPile.add(fighters.remove(i));
        }

        //Resets the players bets
        for(int i = 0; i < players.size(); i++){
            players.get(i).bets = new ArrayList<>();
        }
    }

    public boolean detectMagic(int idx, int spell, int target) {
        //Check if its this players turn
        if(idx != playerTurn) {
            return false;
        }
        //Checks if you have the spell in your hand
        if(spell >= players.get(idx).hand.size() || spell < 0){
            return false;
        }
        //Checks if target is valid
        if(target < 1 || target > 5){
            return false;
        }
        //Reveals their cards
        revealCards(idx, target - 1);
        //Removes card from your hand
        discardPile.add(players.get(idx).hand.remove(spell));
        //Moves turn to the next player
        playerTurn = (playerTurn + 1) % players.size();
        return true;
    }

    //Reveals all face down spell cards on a given player
    public void revealCards(int idx, int fighter){
        for(int i = 0; i < fighters.get(fighter).spells.size(); i++){
            fighters.get(fighter).spells.get(i).isFaceUp.set(idx, true);
        }
    }

    public FighterCard drawFighterCard() { return this.decks.fighterCards.remove(randGen.nextInt(this.decks.fighterCards.size())); }

    public SpellCard drawSpellCard(){
        return this.decks.spellCards.remove(randGen.nextInt(this.decks.spellCards.size()));
    }

    public JudgeCard drawJudgeCard(){
        return this.decks.judgeCards.remove(randGen.nextInt(this.decks.judgeCards.size()));
    }

}
