package com.example.bharath.crazymaze;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends ActionBarActivity{
    TextView display;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar

        getWindow().setFlags(0xFFFFFFFF, WindowManager.LayoutParams.FLAG_FULLSCREEN| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






    }

    public void callLevel(View view){
        Intent i = new Intent(this,LevelSelector.class);
        i.putExtra("setClick",1);
        startActivity(i);
    }
    public void callIntro(View view){
        Intent i = new Intent(this,Intro.class);

        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
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

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }*/
}