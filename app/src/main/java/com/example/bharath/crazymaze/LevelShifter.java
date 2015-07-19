package com.example.bharath.crazymaze;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class LevelShifter extends ActionBarActivity {
    Typeface font;
    TextView display;
    private int level;
    private int score;
    private int totalScore = 0;
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar

        getWindow().setFlags(0xFFFFFFFF, WindowManager.LayoutParams.FLAG_FULLSCREEN| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_shifter);
        scoreText = (TextView)findViewById(R.id.scoreText);
        font =Typeface.createFromAsset(this.getAssets(),"Pacifico.ttf");
        display = (TextView)findViewById(R.id.display);
        display.setTypeface(font);
        display.setTextSize(30);
        display.setText("Move on to next Level");
        scoreText.setTypeface(font);
        scoreText.setTextSize(16);
        Bundle levelSelector = getIntent().getExtras();
        if(levelSelector == null){
            return;
        }
        level = levelSelector.getInt("level");
        score = levelSelector.getInt("score");
        scoreText.setText("Score: "+score);
        totalScore+=score;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_shifter, menu);
        return true;

    }
    public void selectLevel(View view){

       switch(level){

           case 1: Intent i = new Intent(this,Level2.class);
                    i.putExtra("changeScore",score);
                    startActivity(i);
                    break;
           case 2: Intent i2 = new Intent(this,Level3.class);
               i2.putExtra("changeScore",score);
               startActivity(i2);
               break;
           case 3: Intent i3 = new Intent(this,LevelSelector.class);
               startActivity(i3);
               break;
       }

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
