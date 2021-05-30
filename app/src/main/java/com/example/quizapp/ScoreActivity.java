package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        LinearLayout try_again = findViewById(R.id.tryag_bt);
        TextView tv = findViewById(R.id.score_view);

        ProgressBar pb = findViewById(R.id.progress_bar);
        Intent i = getIntent();
        int score = i.getIntExtra(MainActivity.SCR_MESSAGE,0);
        int total = i.getIntExtra(MainActivity.TTL_MESSAGE,0);

        pb.setProgress( score );
        tv.setText(score/10+" / "+total);

//        Intent it = new Intent(this,StartActivity.class);

        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                startActivity(it);

            }
        });


    }
}