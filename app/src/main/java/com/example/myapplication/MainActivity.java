package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import java.util.Timer;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class MainActivity extends AppCompatActivity {

    private FrameLayout gameFrame;
    private LinearLayout startLayout;
    private int frameHeight, frameWidth, initialFrameWidth;

    //Image

    private ImageView player1, player2, fussball;


    //Size

    private int player1Size, player2Size, ballSize;

    //Position

    private float player1X, player1Y;
    private float player2X, player2Y;
    private float ballX, ballY;

    //Score

    private TextView scorePlayer1, scorePlayer2;

    private int score1, score2;
    //Status

    private boolean start_flg = false;
    TextView pitchText;
    TextView noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudioDispatcher dispatcher =
                AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);


        pitchText = (TextView) findViewById(R.id.scorePlayer1);
      noteText = (TextView) findViewById(R.id.scorePlayer2);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processPitch(pitchInHz);


                    }
                });
            }


        };
        // pitchText.setText("geht  dsafsdf");

        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);


        Thread audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();


        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
       // fussball = findViewById(R.id.fussball);
        scorePlayer1 = findViewById(R.id.scorePlayer1);
        scorePlayer2 = findViewById(R.id.scorePlayer2);
    }

    public void processPitch(float pitchInHz) {

        pitchText.setText("geht " + pitchInHz);
        float laenge = frameWidth/7;

        //  if(pitchInHz >= 80 && pitchInHz < 220) {
        //    action_flg = true;


        if (pitchInHz >= 80 && pitchInHz < 100) {
            player1X = 0;
        } else if (pitchInHz >= 100 && pitchInHz < 120) {
            player1X = laenge;
        } else if (pitchInHz >= 120 && pitchInHz < 140) {
            player1X = 2*laenge;
        } else if (pitchInHz >= 140 && pitchInHz < 160) {
            player1X = 3*laenge;
        } else if (pitchInHz >= 160 && pitchInHz <= 180) {
            player1X = 4*laenge;
        } else if (pitchInHz >= 180 && pitchInHz < 200) {
            player1X = 5*laenge;
        } else if (pitchInHz >= 200 && pitchInHz < 220) {
            player1X = 6 *laenge;
        }

        if(start_flg) {
            player1.setX(player1X);
            player2.setX(player2X);
            pitchText.setText("geht1333     " + pitchInHz);
        }

        //   }else{
        //     action_flg = false;
        //   }
    }


    public void startGame(View view) {
        start_flg = true;
        startLayout.setVisibility(View.INVISIBLE);

        if ( frameHeight == 0 ) {
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            initialFrameWidth = frameWidth;

            player1Size = frameWidth/7;
            player2Size = frameWidth/7;

            player1X = player1.getX();
            player1Y = player1.getY();

            player2X = player2.getX();
            player2Y = player2.getY();
        }

        player1.setX(375.0f);
        player2.setX(375.0f);

        //fussball.setX(500.0f);
       // fussball.setY(670.0f);

        //fussball.setVisibility(View.VISIBLE);
        player1.setVisibility(View.VISIBLE);
        player2.setVisibility(View.VISIBLE);

        score1 = 0;
        score2 = 0;
        pitchText.setText("start");
        scorePlayer1.setText("Score: 0");
        scorePlayer2.setText("Score: 0");


        /*timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(start_flg) {
                    handler.post(new Runnable(){
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }
        },0,20);*/


    }
    public void quitGame(View view) {

    }
}
