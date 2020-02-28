package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private Drawable moleImage;
    private ImageView[] imageViews;
    private int moleLocation;
    private int moleInt;
    private ConstraintLayout layout;

    public Button start;
    public Button settings;
    public Button help;

    public EditText scoreBox;
    public TextView timeBox;
    public int count;
    public UpdateMole update;
    public UpdateTime update2;

    public Random rand;
    public Handler handler;
    public boolean on;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = findViewById(R.id.gridLayout);

        moleImage = getDrawable(R.drawable.mole);

        start = findViewById(R.id.button);
        settings = findViewById(R.id.button3);
        help = findViewById(R.id.button2);

        layout = findViewById(R.id.layout);
        handler = new Handler();
        imageViews = new ImageView[16];

        scoreBox = findViewById(R.id.scoreBox);
        timeBox = findViewById(R.id.timeBox);
        count = 30;
        update = new UpdateMole();
        update2 = new UpdateTime();

        rand = new Random();
        on = false;
        moleLocation = rand.nextInt(16);
        moleInt = 1;

        for(int i=0; i<16; i++){
            imageViews[i] = (ImageView) getLayoutInflater().inflate(R.layout.mole_view, null);
            imageViews[i].setMinimumWidth(270);
            imageViews[i].setMinimumHeight(270);
            if(i==moleLocation){
                imageViews[i].setImageDrawable(moleImage);
            }
            grid.addView(imageViews[i]);
        }
    }

    public void startPressed (View v){
        if(on) {
            start.setText("START");
            on = false;
            handler.removeCallbacks(update);
            handler.removeCallbacks(update2);
        }
        else {
            on = true;
            start.setText("STOP");
            count = 30;
            double scoreValue = 0;
            scoreBox.setText(String.format("%.0f", scoreValue));
            handler.postDelayed(update, 1000);
            handler.postDelayed(update2, 1000);
        }

    }

    public void moleCheck(View v) {
        if(on) {
            if (v == imageViews[moleLocation]) {
                whack();
            } else {
                miss();
            }
        }

    }

    public void whack() {
        imageViews[moleLocation].setImageDrawable(null);
        String score = scoreBox.getText().toString();
        double scoreValue = Double.parseDouble(score);
        scoreValue++;
        scoreBox.setText(String.format("%.0f", scoreValue));
    }

    public void miss() {
        String score = scoreBox.getText().toString();
        double scoreValue = Double.parseDouble(score);
        scoreValue--;
        scoreBox.setText(String.format("%.0f", scoreValue));
    }


    public void newMole (View v){
        Intent i = new Intent(this, ChangeAvatar.class);
        i.putExtra("MOLE", moleInt);
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        moleInt = data.getIntExtra("MOLE", 1);
        if(moleInt == 1){
            this.moleImage = getDrawable(R.drawable.mole);
            imageViews[moleLocation].setImageDrawable(moleImage);
            imageViews[moleLocation].setMaxWidth(270);
            imageViews[moleLocation].setMaxHeight(270);
        }
        else if(moleInt == 2){
            this.moleImage = getDrawable(R.drawable.fish3);
            imageViews[moleLocation].setImageDrawable(moleImage);
            imageViews[moleLocation].setMaxWidth(270);
            imageViews[moleLocation].setMaxHeight(270);
        }
        else{
            this.moleImage = getDrawable(R.drawable.crocodile2);
            imageViews[moleLocation].setImageDrawable(moleImage);
            imageViews[moleLocation].setMaxWidth(270);
            imageViews[moleLocation].setMaxHeight(270);
        }
    }

    public void helpPressed(View v){
        Intent i = new Intent(this, HelpActivity.class);
        startActivity(i);
    }


    private class UpdateMole implements Runnable{

        public void run(){
            imageViews[moleLocation].setImageDrawable(null);
            moleLocation = rand.nextInt(16);
            imageViews[moleLocation].setImageDrawable(moleImage);
            handler.postDelayed(update, 1000);
        }

    }

    private class UpdateTime implements Runnable{
        public void run() {
            count--;
            timeBox.setText(count + "");
            String input = timeBox.getText().toString();
            double timeValue = Double.parseDouble(input);
            if (timeValue <= 0) {
                start.setText("RESTART");
                on = false;
                count = 0;
                handler.removeCallbacks(update);
                handler.removeCallbacks(update2);
            } else {
                handler.postDelayed(update2, 1000);
            }
        }
    }

}
