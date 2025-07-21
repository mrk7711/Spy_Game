package com.example.spygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Spinner playerCountSpinner, spyCountSpinner, timeSpinner,wordSpinner;
    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerCountSpinner = findViewById(R.id.playerCountSpinner);
        spyCountSpinner = findViewById(R.id.spyCountSpinner2);
        timeSpinner = findViewById(R.id.timeSpinner2);
        wordSpinner = findViewById(R.id.wordSpinner);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            int players = Integer.parseInt(playerCountSpinner.getSelectedItem().toString());
            int spies = Integer.parseInt(spyCountSpinner.getSelectedItem().toString());
            int time=Integer.parseInt(timeSpinner.getSelectedItem().toString());
            String selectedCategory = wordSpinner.getSelectedItem().toString();

            if (spies >= players) {
                Toast.makeText(this, "تعداد جاسوس‌ها باید کمتر از بازیکنان باشد", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, RoleActivity.class);
            intent.putExtra("players", players);
            intent.putExtra("spies", spies);
            intent.putExtra("time", time);
            intent.putExtra("word", selectedCategory);
            startActivity(intent);
        });
    }
}