package com.example.gamestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Player> players= new ArrayList<Player>();
        players.add(new Player("p1", 2));
        players.add(new Player("p2", 2));
        players.add(new Player("p3", 2));

        TextView display = (TextView)findViewById(R.id.editTextTextMultiLine);
        display.setText("");

        Button runTest = (Button)findViewById(R.id.runTestButton);
        runTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                display.setText("");
                GameState firstInstance = new GameState(players);
                GameState secondInstance = new GameState(firstInstance, 0);
                display.append("--- First Instance ---\n" + firstInstance.toString() + "\n");
                firstInstance.placeBets(0, new ArrayList<>(Arrays.asList(0)));
                display.append("Player 1 bet on " + firstInstance.fighters.get(0).name + ".\n");
                firstInstance.placeBets(1, new ArrayList<>(Arrays.asList(1,2)));
                display.append("Player 2 bet on " + firstInstance.fighters.get(1).name + " and " +
                        firstInstance.fighters.get(2).name + ".\n");
                firstInstance.placeBets(2, new ArrayList<>(Arrays.asList(2,3,4)));
                display.append("Player 3 bet on " + firstInstance.fighters.get(2).name + " and " +
                        firstInstance.fighters.get(3).name + " and " + firstInstance.fighters.get(4).name
                        + ".\n");
                firstInstance.playSpellCard(0, 0, 1);
                display.append("Player 1 played their " + firstInstance.fighters.get(0).spells.get(0).name +
                        " on the " + firstInstance.fighters.get(0).name + ".\n");
                firstInstance.detectMagic(1, 0, 2);
                display.append("Player 2 used detect magic on " + firstInstance.fighters.get(1).name +
                        " using " + firstInstance.discardPile.get(0).name + ".\n");
                firstInstance.pass(2);
                display.append("Player 3 passed.\n");
                firstInstance.pass(0);
                display.append("Player 1 passed.\n");
                firstInstance.pass(1);
                display.append("Player 2 passed.\nThe round is over and their coins are now: \n" +
                        "PL1: " + firstInstance.players.get(0).coins +
                        "\nPL2: " + firstInstance.players.get(1).coins +
                        "\nPL3: " + firstInstance.players.get(2).coins + "\n");
                firstInstance.discardCards(0, new ArrayList<>(Arrays.asList(0,1,2)));
                display.append("Player 1 discarded " + firstInstance.discardPile.get(1).name + " and " +
                        firstInstance.discardPile.get(2).name + " and " + firstInstance.discardPile.get(3).name +
                        ".\n");
                GameState thirdInstance = new GameState(players);
                GameState fourthInstance = new GameState(thirdInstance, 0);
                //These aren't exactly the same because of random number generation
                display.append("\n--- Second Instance ---\n" + secondInstance.toString() + "\n");
                display.append("\n--- Fourth Instance ---\n" + fourthInstance.toString() + "\n");
            }
        });

    }

}