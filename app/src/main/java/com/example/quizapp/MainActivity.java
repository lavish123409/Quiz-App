package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ArrayList<QuizItem> quiz_item = new ArrayList<>();


    private TextView scorebox;
    private TextView quesbox;

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;

    private ProgressBar prog;


    int currques = 0,score = 0;
    public static String SCR_MESSAGE = "score",TTL_MESSAGE = "total";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scorebox = (TextView) findViewById(R.id.scorebox);
        quesbox = (TextView) findViewById(R.id.textView);

        b1= (Button) findViewById(R.id.option1);
        b2= (Button) findViewById(R.id.option2);
        b3= (Button) findViewById(R.id.option3);
        b4= (Button) findViewById(R.id.option4);

        prog= (ProgressBar) findViewById(R.id.prg_bar);

        hideEverything();
        prog.setVisibility(View.VISIBLE);

        load();


        b1.setOnClickListener((View.OnClickListener) this);
        b2.setOnClickListener((View.OnClickListener) this);
        b3.setOnClickListener((View.OnClickListener) this);
        b4.setOnClickListener((View.OnClickListener) this);

    }



    @SuppressLint("SetTextI18n")
    private void updateQuestion() {

        enableButtons();

        quesbox.setText( quiz_item.get(currques).getQuestion());

        String[] ops = quiz_item.get(currques).getOptions();

        b1.setText( ops[0] );
        b2.setText( ops[1] );
        b3.setText( ops[2] );
        b4.setText( ops[3] );


        b1.setBackgroundColor(getResources().getColor(R.color.white));
        b2.setBackgroundColor(getResources().getColor(R.color.white));
        b3.setBackgroundColor(getResources().getColor(R.color.white));
        b4.setBackgroundColor(getResources().getColor(R.color.white));



        scorebox.setText("Score : "+score);


        currques++;


    }

    public void load() {

        // Instantiate the RequestQueue.

        String url ="https://opentdb.com/api.php?amount=10&category=11&difficulty=easy&type=multiple";


        // Request a string response from the provided URL.
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    try {
                        updateQA(response.getJSONArray("results"));
                        prog.setVisibility(View.INVISIBLE);
                        showEverything();
                        updateQuestion();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
                    Toast.makeText(MainActivity.this, "Something went wrong !!", Toast.LENGTH_LONG).show();
                    Log.d("failed",error.getMessage() );
                });

        // Add the request to the RequestQueue.
        MySingelton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }


    private void updateQA(JSONArray results) throws JSONException {

        String ques,cAns,op1,op2,op3;


        for(int i = 0 ; i < results.length() ; i++) {
            JSONObject obj = results.optJSONObject(i);


            ques = String.valueOf(Html.fromHtml(obj.getString("question")));
            cAns = String.valueOf(Html.fromHtml(obj.getString("correct_answer")));



            op1 = String.valueOf(Html.fromHtml(obj.getJSONArray("incorrect_answers").getString(0)));
            op2 = String.valueOf(Html.fromHtml(obj.getJSONArray("incorrect_answers").getString(1)));
            op3 = String.valueOf(Html.fromHtml(obj.getJSONArray("incorrect_answers").getString(2)));

            quiz_item.add(new QuizItem(ques,cAns,op1,op2,cAns,op3));


        }

    }

    @Override
    public void onClick(View v) {

        Button bt = null;
        disableButtons();

        if( v instanceof  Button)
            bt = (Button) v;

        if( bt!=null && bt.getText() == quiz_item.get(currques - 1).getCorrectAns() ) {
            score += 10;
            bt.setBackgroundColor(getResources().getColor(R.color.correct));

        }
        else if (bt != null) {
            bt.setBackgroundColor(getResources().getColor(R.color.incorrect));
        }

        Intent it = new Intent(this,ScoreActivity.class);

        (new Handler()).postDelayed(() -> {
            if(currques < quiz_item.size())
                updateQuestion();
            else {
                it.putExtra(SCR_MESSAGE,score);
                it.putExtra(TTL_MESSAGE,quiz_item.size());
                startActivity(it);
                finish();
            }
        },1000);

    }


    void enableButtons(){

        b1.setClickable(true);
        b2.setClickable(true);
        b3.setClickable(true);
        b4.setClickable(true);

    }


    void disableButtons(){

        b1.setClickable(false);
        b2.setClickable(false);
        b3.setClickable(false);
        b4.setClickable(false);

    }


    void  hideEverything(){

        scorebox.setVisibility(View.INVISIBLE);
        quesbox.setVisibility(View.INVISIBLE);

        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        b4.setVisibility(View.INVISIBLE);

    }

    void  showEverything(){

        scorebox.setVisibility(View.VISIBLE);
        quesbox.setVisibility(View.VISIBLE);

        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
        b4.setVisibility(View.VISIBLE);

    }



}