package our.awesome.SoundBall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import our.awesome.SoundBall.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MenuActivity extends Activity implements View.OnClickListener {
    protected ImageView lastWinner;
    MainActivity main = new MainActivity();
    private Button btnStart, btnLevel1, btnLevel2, btnLevel3, btnQuit;
    private int speed = 1;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private View rl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.Banner1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1291138506652578/7215105841");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        rl = findViewById(R.id.relativeLayout);


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

        lastWinner = findViewById(R.id.lastWinner);

        try {
            Bundle extras = getIntent().getExtras();
            int showbanner = extras.getInt("Banner");
            int sieger = extras.getInt("sieger");
            if (showbanner != 0) {
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
            }
            if (sieger != 0) {
                if (sieger == 1)
                    lastWinner.setImageResource(R.drawable.player1wins);
                else
                    lastWinner.setImageResource(R.drawable.player2wins);

            }

        } catch (Exception e) {

        }


        btnLevel1.setAlpha(1);


    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.btnStart:

                speed = 7;
                mainActivity(view);
                break;
            case R.id.btnLevel1:
                speed = 7;
                if (btnLevel1.getAlpha() != 1) {
                    btnLevel1.setAlpha(1);
                    btnLevel2.setAlpha(0.7f);
                    btnLevel3.setAlpha(0.7f);
                }
                break;
            case R.id.btnLevel2:
                speed = 9;
                if (btnLevel2.getAlpha() != 1) {
                    btnLevel2.setAlpha(1);
                    btnLevel3.setAlpha(0.7f);
                    btnLevel1.setAlpha(0.7f);
                }
                break;
            case R.id.btnLevel3:
                speed = 11;
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


    public void mainActivity(View view) {
        Intent intentT = new Intent(this, main.getClass());

        intentT.putExtra("level", speed);
        startActivity(intentT);
        finish();
    }


    public void quit(View view) {
        finish();
        moveTaskToBack(true);
    }


}
