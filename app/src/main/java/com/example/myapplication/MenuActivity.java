package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MenuActivity extends Activity implements View.OnClickListener {
    private Button btnStart, btnLevel1, btnLevel2, btnLevel3, btnQuit;
    protected ImageView winner;
    MainActivity main = new MainActivity();



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


        switch(view.getId()){

            case R.id.btnStart:
                mainActivity();
            case R.id.btnLevel1:
                main.level1(view);
                if (btnLevel1.getAlpha() != 1) {
                    btnLevel1.setAlpha(1);
                    btnLevel2.setAlpha(0.7f);
                    btnLevel3.setAlpha(0.7f);
                }
                break;
            case R.id.btnLevel2:
                main.level2(view);
                if (btnLevel2.getAlpha() != 1) {
                    btnLevel2.setAlpha(1);
                    btnLevel3.setAlpha(0.7f);
                    btnLevel1.setAlpha(0.7f);
                }
                break;
            case R.id.btnLevel3:
                main.level3(view);
                if (btnLevel3.getAlpha() != 1) {
                    btnLevel3.setAlpha(1);
                    btnLevel2.setAlpha(0.7f);
                    btnLevel1.setAlpha(0.7f);
                }
                break;
            case R.id.btnQuit:
                quit(view);
                break;
            default:
                break;

        }

    }


    public void mainActivity() {
        Intent intentT = new Intent(this, main.getClass());
        startActivity(intentT);
    }

    public void quit(View view){
        finish();
        moveTaskToBack(true);
    }
}
