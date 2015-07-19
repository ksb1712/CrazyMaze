package com.example.bharath.crazymaze;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class LevelSelector extends ActionBarActivity {
    int level;
    ImageButton levelOne,levelTwo,levelThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar

        getWindow().setFlags(0xFFFFFFFF, WindowManager.LayoutParams.FLAG_FULLSCREEN| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
        levelOne = (ImageButton)findViewById(R.id.levelOne);
        levelTwo = (ImageButton)findViewById(R.id.levelTwo);
        levelThree = (ImageButton)findViewById(R.id.levelThree);
        Bundle setCLick = getIntent().getExtras();
        if(setCLick == null){
            return;
        }
        level = setCLick.getInt("setClick");
        if(level==1){
            levelOne.setEnabled(true);
            levelTwo.setEnabled(false);
            levelThree.setEnabled(true);
        }
        if(level==2){
            levelOne.setEnabled(true);
            levelTwo.setEnabled(true);
            levelThree.setEnabled(false);
        }
        if(level==3){
            levelOne.setEnabled(true);
            levelTwo.setEnabled(true);
            levelThree.setEnabled(true);
        }

    }
    public void callLevelOne(View view){

        Intent i = new Intent(this,Level1.class);
        startActivity(i);


    }
    public void callLevel2(View view){

        Intent i = new Intent(this,Level2.class);
        i.putExtra("changeScore",0);

        startActivity(i);


    }
    public void callLevelThree(View view){

        Intent i = new Intent(this,Level3.class);
        i.putExtra("changeScore",0);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level_selector, menu);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();*/
    }
}
