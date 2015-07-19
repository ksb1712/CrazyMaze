package com.example.bharath.crazymaze;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import java.util.Timer;
import java.util.TimerTask;

public class Level1 extends ActionBarActivity {

    BallView mBallView = null;
    Bitmap line,back;
    Handler RedrawHandler = new Handler(); //so redraw occurs in main thread
    Timer mTmr = null;
    TimerTask mTsk = null;
    int mScrWidth, mScrHeight;
    android.graphics.PointF mBallPos, mBallSpd;
    float ballx = 0,bally=0;
    FrameLayout mainView;
    long startTime = System.currentTimeMillis();
    int score=0;
    MediaPlayer ourSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar

        getWindow().setFlags(0xFFFFFFFF, WindowManager.LayoutParams.FLAG_FULLSCREEN| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
       mainView =(android.widget.FrameLayout)findViewById(R.id.main_view);
        Display display = getWindowManager().getDefaultDisplay();
        mScrWidth = display.getWidth();
        mScrHeight = display.getHeight();
        mBallPos = new android.graphics.PointF();
        mBallSpd = new android.graphics.PointF();

        back = BitmapFactory.decodeResource(getResources(),R.drawable.back4);
        ourSong = MediaPlayer.create(Level1.this,R.raw.music);
        ourSong.start();
        //create variables for ball position and speed
        mBallPos.x = 140;
        mBallPos.y = mScrHeight/2+150;
        mBallSpd.x = 0;
        mBallSpd.y = 0;
        mBallView = new BallView(this, mBallPos.x, mBallPos.y, 5);

        mainView.addView(mBallView); //add ball to main screen
        mBallView.invalidate();
        ((SensorManager)getSystemService(Context.SENSOR_SERVICE)).registerListener(
                new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {

                        mBallSpd.x = -event.values[0];
                        mBallSpd.y = event.values[1];

                    }
                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
                },
                ((SensorManager)getSystemService(Context.SENSOR_SERVICE))
                        .getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),
                SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add("Exit");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getTitle() == "Exit")
            finish();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPause()
    {
        mTmr.cancel(); //kill timer
        mTmr = null;
        mTsk = null;
        super.onPause();
    }
    public void startIntent(){
        ourSong.stop();
        Intent i = new Intent(this,GameOver.class);
        i.putExtra("level",1);
        i.putExtra("score",score);
        startActivity(i);

    }
    public void changeLevel(){
        ourSong.stop();
        long endTime = System.currentTimeMillis()-startTime;
        endTime=endTime/1000;
        if(endTime<=10)score=200;
        else if((endTime>10)&&(endTime<=15))score=100;
        else score = 50;
        Intent i = new Intent(this,LevelShifter.class);
        i.putExtra("level",1);
        i.putExtra("score",score);
        startActivity(i);

    }
    public void onResume()
    {
        //create timer to move ball to new position
        mTmr = new Timer();
        mTsk = new TimerTask() {
            public void run() {
                ballx = mBallPos.x;
                bally = mBallPos.y;
                mBallPos.x += mBallSpd.x;
                mBallPos.y += mBallSpd.y;

                if(back.getPixel((int)(mBallPos.x+27),(int)(mBallPos.y))== Color.BLACK)startIntent();
                else  if(back.getPixel((int)(mBallPos.x-27),(int)(mBallPos.y))==Color.BLACK)startIntent();
                 else if(back.getPixel((int)(mBallPos.x),(int)(mBallPos.y+27))==Color.BLACK)startIntent();
                 else if(back.getPixel((int)(mBallPos.x),(int)(mBallPos.y-27))==Color.BLACK)startIntent();
                if(back.getPixel((int)(mBallPos.x+27),(int)(mBallPos.y))== Color.BLUE)mBallPos.x-=mBallSpd.x;
                 if(back.getPixel((int)(mBallPos.x-27),(int)(mBallPos.y))== Color.BLUE)mBallPos.x-=mBallSpd.x;
                 if(back.getPixel((int)(mBallPos.x),(int)(mBallPos.y+27))== Color.BLUE)mBallPos.y-=mBallSpd.y;
                if(back.getPixel((int)(mBallPos.x-27),(int)(mBallPos.y))==Color.YELLOW)changeLevel();
                if(back.getPixel((int)(mBallPos.x+27),(int)(mBallPos.y))==Color.YELLOW)changeLevel();
                mBallView.x = mBallPos.x;
                mBallView.y = mBallPos.y;

                RedrawHandler.post(new Runnable() {
                    public void run() {
                        mBallView.invalidate();
                    }});
            }};
        mTmr.schedule(mTsk,10,10);
        super.onResume();
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();

        System.runFinalizersOnExit(true);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }
    public class BallView extends View {

        public float x;
        public float y;
        private final int r;
        private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //construct new ball object
        public BallView(Context context, float x, float y, int r) {
            super(context);

            mPaint.setColor(Color.RED);
            this.x = x;
            this.y = y;
            this.r = r;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(back,0,0,mPaint);


            canvas.drawCircle(x, y, 25, mPaint);


        }
    }

}






