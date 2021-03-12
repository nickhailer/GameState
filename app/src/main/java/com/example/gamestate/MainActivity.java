package com.example.gamestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
       GameState gameState = new GameState(players);

        Button runTest = (Button)findViewById(R.id.runTestButton);
        runTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gameState.toString();
            }
        });

    }

}