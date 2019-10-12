package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.Button;
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


import java.util.Random;
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

    //PauseButton
    Button pauseButton;
    //Image
    ImageView player1, player2, fussball;
    //timer
    TextView sekAnzeige;
    Boolean play1IstDran;
    //Richtung
    float randomx;
    float randomy;
    float randomxminus;
    float randomyminus;
    //Threadscheise
    Thread barBewegen = new Thread();
    Timer timer;
    Task1 task1;
    Handler handler1 = new Handler();
    //Ball
    private float[] direction; //direction modifier (-1,1)
    //Layout
    private int speed = 5;
    private RectF oval;
    private int size;
    //Score
    private Button level1, level2, level3;
    private FrameLayout gameFrame;
    private LinearLayout startLayout;
    private int frameHeight, frameWidth;
    private ImageView goal;
    private boolean goalVisible = false;
    private float player1X, player1Y;
    private float player2X, player2Y;
    private int initialPosY;
    private int initialPosX;
    private TextView scorePlayer1, scorePlayer2;
    private int score1, score2;

    public void level1(View view) {
        this.speed = 5;
        if (level1.getAlpha() != 1) {
            level1.setAlpha(1);
            level2.setAlpha(0.7f);
            level3.setAlpha(0.7f);
        }
    }

    public void level2(View view) {
        this.speed = 7;
        if (level2.getAlpha() != 1) {
            level2.setAlpha(1);
            level1.setAlpha(0.7f);
            level3.setAlpha(0.7f);
        }
    }

    public void level3(View view) {
        this.speed = 9;
        if (level3.getAlpha() != 1) {
            level3.setAlpha(1);
            level2.setAlpha(0.7f);
            level1.setAlpha(0.7f);
        }
    }

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
        goal = findViewById(R.id.goal);
        sekAnzeige = findViewById(R.id.sekAnzeige);
        sekAnzeige.setVisibility(View.INVISIBLE);
        pauseButton= findViewById(R.id.btnPauseResume);


        level1 = findViewById(R.id.btnLevel1);
        level2 = findViewById(R.id.btnLevel2);
        level3 = findViewById(R.id.btnLevel3);
    }

    public void processPitch(float pitchInHz) {

        final float laenge = frameWidth / 5;
        Runnable r = new Runnable() {
            @Override
            public void run() {

            }
        };
        if (pitchInHz >= 80 && pitchInHz < 700) {


            if (pitchInHz >= 80 && pitchInHz < 120) {

                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 15; i++) {
                            if (player1X > 0) {
                                player1X = player1X - 2;
                            }
                            if (player1X < 0) {
                                player1X = player1X + 2;
                            }
                            if (play1IstDran)
                                player1.setX(player1X);
                            else
                                player2.setX(player1X);
                        }
                    }
                };


            } else if (pitchInHz >= 120 && pitchInHz < 180) {


                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 15; i++) {
                            if (player1X > laenge) {
                                player1X = player1X - 2;
                            }
                            if (player1X < laenge) {
                                player1X = player1X + 2;
                            }
                            if (play1IstDran)
                                player1.setX(player1X);
                            else
                                player2.setX(player1X);
                        }
                    }
                };


            } else if (pitchInHz >= 180 && pitchInHz < 260) {
                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            if (player1X > 2 * laenge) {
                                player1X = player1X - 2;
                            }
                            if (player1X < 2 * laenge) {
                                player1X = player1X + 2;
                            }
                            if (play1IstDran)
                                player1.setX(player1X);
                            else
                                player2.setX(player1X);
                        }
                    }
                };
            } else if (pitchInHz >= 260 && pitchInHz < 530) {
                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            if (player1X > 3 * laenge) {
                                player1X = player1X - 2;
                            }
                            if (player1X < 3 * laenge) {
                                player1X = player1X + 2;
                            }
                            if (play1IstDran)
                                player1.setX(player1X);
                            else
                                player2.setX(player1X);
                        }
                    }
                };
            } else if (pitchInHz >= 530 && pitchInHz <= 700) {

                r = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            if (player1X > 4 * laenge) {
                                player1X = player1X - 2;
                            }
                            if (player1X < 4 * laenge) {
                                player1X = player1X + 2;
                            }
                            if (play1IstDran)
                                player1.setX(player1X);
                            else
                                player2.setX(player1X);
                        }
                    }
                };
            }

            barBewegen = new Thread(r);
            barBewegen.start();

        }


    }


    public void setzteRichtung() {
        //Direction
        randomx = (float) new Random().nextFloat();

        randomy = (float) new Random().nextFloat();
        randomxminus = (float) new Random().nextFloat();
        randomyminus = (float) new Random().nextFloat();
        if (randomxminus > 0.5f) randomx = -randomx;

        if (randomyminus > 0.5f) {
            randomy = -randomy;
            play1IstDran = false;
        } else {
            play1IstDran = true;
        }

        if (randomx < 0.5) {
            direction = new float[]{randomx, randomy};
        } else {
            direction = new float[]{randomx - 0.2f, randomy};
        }

    }

    AudioDispatcher dispatcher;
    PitchDetectionHandler pdh;
    AudioProcessor pitchProcessor;
    Thread audioThread;
    public void startGame(View view) {

        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        setzteRichtung();
        task1 = new Task1();

        timer = new Timer();


        pdh = new PitchDetectionHandler() {

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

        pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);


        audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();


        if (frameHeight == 0) {
            gameFrame.setLayoutParams(new LinearLayout.LayoutParams(gameFrame.getWidth(), gameFrame.getHeight() * 9 / 10));


            frameHeight = gameFrame.getLayoutParams().height;
            frameWidth = gameFrame.getLayoutParams().width;

            fussball.getLayoutParams().height = frameWidth / 15;
            size = frameWidth / 15;
            fussball.getLayoutParams().width = size;
            fussball.setScaleType(ImageView.ScaleType.FIT_XY);
            player1.getLayoutParams().height = (frameWidth / 5) / 4;
            player2.getLayoutParams().height = (frameWidth / 5) / 4;

            player1.getLayoutParams().width = frameWidth / 5;
            player2.getLayoutParams().width = frameWidth / 5;
            player1.setScaleType(ImageView.ScaleType.FIT_XY);
            player2.setScaleType(ImageView.ScaleType.FIT_XY);
            player1.setX(frameWidth / 2 - player1.getLayoutParams().width / 2);
            player2.setX(frameWidth / 2 - player2.getLayoutParams().width / 2);

            initialPosY = frameHeight / 2 - size / 2;
            initialPosX = frameWidth / 2 - size / 2;


            fussball.setX(initialPosX);
            fussball.setY(initialPosY);

        }

        timer.scheduleAtFixedRate(task1, 5000, 12);
        countdowntimer();


        startLayout.setVisibility(View.INVISIBLE);
        fussball.setVisibility(View.VISIBLE);
        player1.setVisibility(View.VISIBLE);
        player2.setVisibility(View.VISIBLE);

        scorePlayer1.setText("Score: 0");
        scorePlayer2.setText("Score: 0");
        score1 = 0;
        score2 = 0;


    }

    public void move() {

        if (randomxminus > 0.5f)
            fussball.setX(fussball.getX() + speed * (int) direction[0] - 1);
        else
            fussball.setX(fussball.getX() + speed * (int) direction[0] + 1);

        if (randomyminus > 0.5f)
            fussball.setY(fussball.getY() + speed * (int) (direction[1] - 1.0f));
        else
            fussball.setY(fussball.getY() + speed * (int) (direction[1] + 1.0f));


        //Check if ball touches the Player
        if (fussball.getY() >= (player1.getY() - player1.getHeight())) {
            if ((player1.getX() - size / 2 <= fussball.getX()) && (player1.getX() + size / 2 + player1.getWidth() >= fussball.getX())) {

                direction[1] = direction[1] * -1.0f;
                randomyminus = 1 - randomyminus;
                play1IstDran = false;
            }
        }

        if (fussball.getY() <= player2.getY() + player2.getHeight()) {
            if ((player2.getX() - size / 2 <= fussball.getX()) && (player2.getX() + size / 2 + player2.getWidth() >= fussball.getX())) {
                direction[1] = direction[1] * -1.0f;
                randomyminus = 1 - randomyminus;

                play1IstDran = true;
            }
        }

        if (fussball.getX() < 0 || fussball.getX() + size > gameFrame.getWidth()) {
            direction[0] = direction[0] * -1;
            randomxminus = 1 - randomxminus;
        }

        if (fussball.getY() < 0) {
            torGeschossenPlayer1();

        }
        if (fussball.getY() + size > gameFrame.getHeight()) {

            torGeschossenPlayer2();

        }
        // }
    }

    public void torGeschossenPlayer2() {
        timer.cancel();
        task1 = null;
        player1.setX(frameWidth / 2 - player1.getLayoutParams().width / 2);
        player2.setX(frameWidth / 2 - player2.getLayoutParams().width / 2);
        fussball.setX(initialPosX);
        fussball.setY(initialPosY);
        handler1.post(new Runnable() {
            @Override
            public void run() {
                score2++;
                scorePlayer2.setText("Player 2 : " + score2);
                if (score2 >= maxpunkte) {
                    setContentView(R.layout.activity_main);

                }
                countdowntimer();
            }
        });
        setzteRichtung();
        task1 = new Task1();
        timer = new Timer();
        timer.scheduleAtFixedRate(task1, 5000, 12);

    }
    int maxpunkte = 5;
    public void torGeschossenPlayer1() {
        timer.cancel();
        task1 = null;
        player1.setX(frameWidth / 2 - player1.getLayoutParams().width / 2);
        player2.setX(frameWidth / 2 - player2.getLayoutParams().width / 2);
        fussball.setX(initialPosX);
        fussball.setY(initialPosY);
        handler1.post(new Runnable() {
            @Override
            public void run() {
                score1++;
                scorePlayer1.setText("Player 1 : " + score1);
                if (score1 >= maxpunkte) {

                    setContentView(R.layout.activity_main);

                }
            }
        });
        setzteRichtung();
        task1 = new Task1();
        timer = new Timer();
        timer.scheduleAtFixedRate(task1, 5000, 12);

    }


    public void countdowntimer() {
         new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished > 4000) {
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {

                            goal.getLayoutParams().width = frameWidth / 2;
                            goal.getLayoutParams().height = frameWidth / 3;
                            goal.setScaleType(ImageView.ScaleType.FIT_XY);

                            goal.setVisibility(View.VISIBLE);


                        }
                    });
                }
                if (millisUntilFinished < 3000) {

                    handler1.post(new Runnable() {
                        @Override
                        public void run() {

                            goal.setVisibility(View.INVISIBLE);
                            sekAnzeige.setVisibility(View.VISIBLE);

                        }
                    });
                }

                if (millisUntilFinished < 1000) {
                    sekAnzeige.setText("GOOO");
                } else {
                    sekAnzeige.setText("" + millisUntilFinished / 1000);
                }
            }

            public void onFinish() {

                sekAnzeige.setVisibility(View.INVISIBLE);
            }
        }.start();

    }
    boolean pausetrue=false;
    public void pausePushed(View view){

       if(!pausetrue) {
           pausetrue=true;
           timer.cancel();
           timer = null;
           task1 = null;
           pauseButton.setText("Resume");
           audioThread.interrupt();
           dispatcher.stop();







       }
       else {
           pausetrue = false;
           task1 = new Task1();
           timer = new Timer();
           timer.scheduleAtFixedRate(task1, 1000, 12);
           pauseButton.setText("Pause");
           dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);
           pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
           dispatcher.addAudioProcessor(pitchProcessor);

           audioThread = new Thread(dispatcher, "Audio Thread");
           audioThread.start();




       }
    }

    private class Task1 extends TimerTask {

        public void run() {

            move();

        }
    }
}
