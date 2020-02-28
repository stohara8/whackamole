package com.example.whackamole;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeAvatar extends AppCompatActivity {

    private RadioButton moleButton;
    private RadioButton fishButton;
    private RadioButton crocButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        moleButton = findViewById(R.id.moleButton);
        fishButton = findViewById(R.id.fishButton);
        crocButton = findViewById(R.id.crocButton);
        Intent intent = getIntent();
        int moleInt = intent.getIntExtra("MOLE", 1);
        if(moleInt == 1) {
            moleButton.setChecked(true);
        } else if(moleInt == 2) {
            fishButton.setChecked(true);
        } else {
            crocButton.setChecked(true);
        }

    }

    @Override
    public void onBackPressed(){
        int mole;
        if (moleButton.isChecked()){
            mole = 1;
        }
        else if(fishButton.isChecked()){
            mole = 2;
        }
        else{
            mole = 3;
        }
        Intent i = new Intent();
        i.putExtra("MOLE", mole);
        setResult(RESULT_OK, i);
        finish();
    }

}