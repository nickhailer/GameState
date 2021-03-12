package com.example.gamestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.Card.FighterCard;
import com.example.Card.JudgeCard;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Player> players= new ArrayList<Player>();
        players.add(new Player("p1", 2));
        players.add(new Player("p2", 2));
        players.add(new Player("p3", 2));
        GameState firstInstance = new GameState(players);
        GameState secondInstance = new GameState(firstInstance, 0);
        GameState thirdInstance = new GameState((players));
        GameState fourthInstance = new GameState(thirdInstance, 0);

        TextView display = (TextView)findViewById(R.id.editTextTextMultiLine);
        display.setText("");

        Button runTest = (Button)findViewById(R.id.runTestButton);
        runTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText("");
                String gameInfo = firstInstance.toString() + secondInstance.toString()
                        + thirdInstance.toString() + fourthInstance.toString();
                display.setText(gameInfo);
            }
        });

    }

}