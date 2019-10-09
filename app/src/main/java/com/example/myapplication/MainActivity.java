package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
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
import java.util.TimerTask;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class MainActivity extends AppCompatActivity {

    //Ball
    public int[] direction = new int[]{1, 1}; //direction modifier (-1,1)
    public int speed = 13;
    public RectF oval;

    //Image
    ImageView player1, player2, fussball;


    //Size
    TextView pitchText;

    //Position
    TextView noteText;
    //Handler

    //Score
    Handler handler;
    Thread barBewegen = new Thread();
    private FrameLayout gameFrame;
    private LinearLayout totalLayout;
    private LinearLayout startLayout;
    private int frameHeight, frameWidth, initialFrameWidth;
    private TextView goal;

    private float player1X, player1Y;
    private float player2X, player2Y;
    private TextView scorePlayer1, scorePlayer2;
    private int score1, score2;
    //Status


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        fussball = findViewById(R.id.fussball);
        scorePlayer1 = findViewById(R.id.scorePlayer1);
        scorePlayer2 = findViewById(R.id.scorePlayer2);
        totalLayout = findViewById(R.id.totalLayout);
        goal = findViewById(R.id.goalText);

    }

    public void processPitch(float pitchInHz) {

        final float laenge = frameWidth / 7;
        Runnable r = new Runnable() {
            @Override
            public void run() {

            }
        };
        if (pitchInHz >= 70 && pitchInHz < 800) {


            if (pitchInHz >= 70 && pitchInHz < 90) {

                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 6; i++) {
                            if (player1X > 0) {
                                player1X = player1X - 3;
                            }
                            player1.setX(player1X);
                            player2.setX(player1X);

                        }
                    }
                };


            } else if (pitchInHz >= 90 && pitchInHz < 120) {


                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 6; i++) {
                            if (player1X > laenge) {
                                player1X = player1X - 3;
                            }
                            if (player1X < laenge) {
                                player1X = player1X + 3;
                            }
                            player1.setX(player1X);
                            player2.setX(player1X);
                        }
                    }
                };


            } else if (pitchInHz >= 120 && pitchInHz < 200) {
                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 6; i++) {
                            if (player1X > 2 * laenge) {
                                player1X = player1X - 3;
                            }
                            if (player1X < 2 * laenge) {
                                player1X = player1X + 3;
                            }
                            player1.setX(player1X);
                            player2.setX(player1X);
                        }
                    }
                };
            } else if (pitchInHz >= 200 && pitchInHz < 300) {
                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 6; i++) {
                            if (player1X > 3 * laenge) {
                                player1X = player1X - 3;
                            }
                            if (player1X < 3 * laenge) {
                                player1X = player1X + 3;
                            }
                            player1.setX(player1X);
                            player2.setX(player1X);
                        }
                    }
                };
            } else if (pitchInHz >= 300 && pitchInHz <= 500) {

                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 6; i++) {
                            if (player1X > 4 * laenge) {
                                player1X = player1X - 3;
                            }
                            if (player1X < 4 * laenge) {
                                player1X = player1X + 3;
                            }
                            player1.setX(player1X);
                            player2.setX(player1X);
                        }
                    }
                };
            } else if (pitchInHz >= 500 && pitchInHz < 700) {
                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 6; i++) {
                            if (player1X > 5 * laenge) {
                                player1X = player1X - 3;
                            }
                            if (player1X < 5 * laenge) {
                                player1X = player1X + 3;
                            }
                            player1.setX(player1X);
                            player2.setX(player1X);
                        }
                    }
                };
            } else if (pitchInHz >= 700 && pitchInHz < 798) {
                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 6; i++) {
                            if (player1X > 5 * laenge) {
                                player1X = player1X - 3;
                            }
                            if (player1X < 5 * laenge) {
                                player1X = player1X + 3;
                            }
                            player1.setX(player1X);
                            player2.setX(player1X);

                        }
                    }
                };
            }

            barBewegen = new Thread(r);
            barBewegen.start();
            player1.setX(player1X);
            player2.setX(player1X);
        }


    }

    public void startGame(View view) {

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        handler = new Handler();
        Task1 task1 = new Task1();
        handler.postDelayed(task1, 30);


        PitchDetectionHandler pdh = new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                processPitch(pitchInHz);

                            }
                        });
            }

        };

        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);


        Thread audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();


        if (frameHeight == 0) {
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            initialFrameWidth = frameWidth;
            fussball.getLayoutParams().height = frameWidth / 15;
            fussball.getLayoutParams().width = frameWidth / 15;
            fussball.setScaleType(ImageView.ScaleType.FIT_XY);

            player1.getLayoutParams().width = frameWidth / 5;
            player2.getLayoutParams().width = frameWidth / 5;
            player1.setScaleType(ImageView.ScaleType.FIT_XY);
            player2.setScaleType(ImageView.ScaleType.FIT_XY);
            player1.setX(frameWidth/2 - player1.getLayoutParams().width/2);
            player2.setX(frameWidth/2- player2.getLayoutParams().width/2);

            float initialPosY = (float) frameHeight/2;
            float initialPosX = (float) frameWidth/2;
            gameFrame.getLayoutParams().height = totalLayout.getHeight()*9/10;


            fussball.setX(initialPosX);
            fussball.setY(initialPosY);

        }


        startLayout.setVisibility(View.INVISIBLE);
        fussball.setVisibility(View.VISIBLE);
        player1.setVisibility(View.VISIBLE);
        player2.setVisibility(View.VISIBLE);

        scorePlayer1.setText("Score: 0");
        scorePlayer2.setText("Score: 0");


    }

    public void move() {

        fussball.setX(fussball.getX() + speed * direction[0]);
        fussball.setY(fussball.getY() + speed * direction[1]);
        int size = fussball.getWidth();
        this.oval = new RectF(fussball.getX() - size / 2,
                fussball.getY() - size / 2, fussball.getX() + size / 2,
                fussball.getY() + size / 2);
        Rect bounds = new Rect();
        this.oval.roundOut(bounds);


        //This is what you're looking for ▼

        if (!gameFrame.getClipBounds(bounds)) {
            if (fussball.getX() < 0 || fussball.getX() + size > gameFrame.getWidth()) {
                direction[0] = direction[0] * -1;
            }

            if (fussball.getY() < 0 || fussball.getY() + size > gameFrame.getHeight()) {
                //direction[1] = direction[1] * -1;

                goal.setText("Goooooall");
                goal.setVisibility(View.VISIBLE);

            }
        }
    }

    public void quitGame(View view) {

    }

    private class Task1 implements Runnable {
        @Override
        public void run() {
            move();
            handler.postDelayed(this, 30);
        }
    }
}
