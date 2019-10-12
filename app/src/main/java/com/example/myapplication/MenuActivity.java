package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MenuActivity extends Activity implements View.OnClickListener {
    private Button btnStart, btnLevel1, btnLevel2, btnLevel3, btnQuit;
    protected ImageView winner;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        btnLevel1 = findViewById(R.id.btnLevel1);
        btnLevel1.setOnClickListener(this);

        btnLevel2 = findViewById(R.id.btnLevel2);
        btnLevel2.setOnClickListener(this);

        btnLevel3 = findViewById(R.id.btnLevel3);
        btnLevel3.setOnClickListener(this);

        btnQuit = findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        MainActivity main = new MainActivity();

        switch(view.getId()){

            case R.id.btnStart:
                mainActivity();
            case R.id.btnLevel1:
                main.level1(view);
                break;
            case R.id.btnLevel2:
                main.level2(view);
                break;
            case R.id.btnLevel3:
                main.level3(view);
                break;
            case R.id.btnQuit:
                quit(view);
                break;
            default:
                break;

        }

    }


    public void mainActivity() {
        Intent intentT = new Intent(this, MainActivity.class);
        startActivity(intentT);
    }

    public void quit(View view){
        finish();
        moveTaskToBack(true);
    }
}
