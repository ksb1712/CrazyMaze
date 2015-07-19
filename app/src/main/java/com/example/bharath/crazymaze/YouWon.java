package com.example.bharath.crazymaze;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class YouWon extends ActionBarActivity {

    Typeface font;
    TextView scoreText;
    TextView highScoreText;
    TextView endText;
    private int level;
    public String file = "scoreFile";
    private int scoreC;
    private int scoreP;
    SharedPreferences prefs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        font = Typeface.createFromAsset(this.getAssets(),"Pacifico.ttf");
        endText = (TextView)findViewById(R.id.endText);
        scoreText = (TextView)findViewById(R.id.scoreText);
        highScoreText = (TextView)findViewById(R.id.highScoreText);
        prefs = getSharedPreferences(file,0);
        prefs = getSharedPreferences(file,0);
        int temp = prefs.getInt("score", 0);

        endText.setTypeface(font);
        endText.setTextSize(25);
        endText.setText("You Won");
        scoreText.setTypeface(font);
        scoreText.setTextSize(16);
        highScoreText.setTypeface(font);
        highScoreText.setTextSize(16);
        Bundle retryLevelSelector = getIntent().getExtras();
        if(retryLevelSelector == null)return;
        level = retryLevelSelector.getInt("level");
        scoreC = retryLevelSelector.getInt("score");
        if(scoreC>scoreP){
            String setScore = ""+scoreC;
            scoreP = scoreC;

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("score",scoreC);
            editor.commit();
        }
        scoreText.setText("Score: "+scoreC);
        highScoreText.setText("High Score: "+ scoreP);

    }

    public void callMenu(View view){
        Intent i = new Intent(this,LevelSelector.class);
        i.putExtra("setClick",level);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_you_won, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
