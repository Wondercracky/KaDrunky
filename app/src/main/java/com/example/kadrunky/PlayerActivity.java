package com.example.kadrunky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    Button addButton, startButton;
    TextView playerName;
    ArrayList<Player> playerNamesList;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerNamesList = new ArrayList<>();
        addButton = findViewById(R.id.addPlayer_Button);
        playerName = findViewById(R.id.addPlayer_Field);
        startButton = findViewById(R.id.startGame_Button);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.playerList_RecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, playerNamesList);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerNamesList.add(new Player(playerName.getText().toString(), false));
                playerName.setText("");
                adapter.notifyDataSetChanged();
            }
        });
        final Intent intent = new Intent(this, MainGameActivity.class);

        //Start Game!!!
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("playerList", playerNamesList);
                startActivity(intent);
            }
        });
    }
}