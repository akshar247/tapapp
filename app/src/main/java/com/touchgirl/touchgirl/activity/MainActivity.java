package com.touchgirl.touchgirl.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.touchgirl.touchgirl.R;
import com.touchgirl.touchgirl.SplashScreen;
import com.touchgirl.touchgirl.config.CommonFunctions;
import com.touchgirl.touchgirl.config.Constants;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    final int min = 2;
    final int max = 5;
    private int count = new Random().nextInt((max - min) + 1) + min;
    private int count7 = new Random().nextInt((max - min) + 1) + min;
    private int count2 = new Random().nextInt((max - min) + 1) + min;
    private int count3 = new Random().nextInt((max - min) + 1) + min;
    private int count4 = new Random().nextInt((max - min) + 1) + min;
    private int count5 = new Random().nextInt((max - min) + 1) + min;
    private int count6 = new Random().nextInt((max - min) + 1) + min;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.text3)
    TextView text3;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.text4)
    TextView text4;
    @BindView(R.id.image4)
    ImageView image4;
    @BindView(R.id.text5)
    TextView text5;
    @BindView(R.id.image5)
    ImageView image5;
    @BindView(R.id.img_girl)
    ImageView img_girl;
    @BindView(R.id.img_new_image)
    Button btn_new_image;
    @BindView(R.id.adView)
    View adContainer;
    private String path1;
    private String path2;
    boolean isFirst, isSecond, isThird, isFour, isFive;
    private InterstitialAd interstitialAd;
    AdView mAdView;
    com.facebook.ads.AdView adView;
    AdRequest adRequest;
    private NativeAdLayout nativeAdLayout;
    private NativeAd nativeAd;
    RewardedVideoAd fbrewardedVideoAd;
    private RewardedAd rewardedAd;
    private int image;
    String ginurl,finurl,gburl,fburl,grewardedurl,frewardedburl;
    com.facebook.ads.InterstitialAd fbinterstitialAd;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    private static final long COUNTER_TIME = 10;
    private static final int GAME_OVER_REWARD = 1;
    private int coinCount;
    private TextView coinCountText;
    private CountDownTimer countDownTimer;
    private boolean gameOver;
    private boolean gamePaused;
    private Button retryButton;
    private Button showVideoButton;
    private long timeRemaining;
    boolean isLoading;
    int cointotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudienceNetworkAds.initialize(this);
        coinCountText = findViewById(R.id.coin_count_text);
//        coinCountText.setText("Coins: " + coinCount--);
        coinCount = CommonFunctions.getPreference(this, Constants.coin, 20);
        coinCountText.setText("Coins: " + coinCount);

//        coinCount--;
        init();
        if (SplashScreen.appConfigModel.IS_GOOGLE.equalsIgnoreCase(Constants.VAL_TRUE)) {
            setInterstitialAd();
        } else {
            load_InterstitialAd_fb();
        }
        if (SplashScreen.appConfigModel.IS_GOOGLE.equalsIgnoreCase(Constants.VAL_TRUE)) {
            load_RewardedVideoAd_admob();
        } else {
            loadRewardedVideoAd_fb();
        }
    }

    private void init() {
        ButterKnife.bind(this);
        AudienceNetworkAds.initialize(this);
        Intent intentReceived = getIntent();
        Bundle data = intentReceived.getExtras();
        if(data != null){
            grewardedurl= data.getString(Constants.GOOGLE_REWARDED_ID);
            frewardedburl= data.getString(Constants.FACEBOOK_REWARDED_ID);
            ginurl= data.getString(Constants.GOOGLE_INTERITIAL_ID);
            finurl= data.getString(Constants.FACEBOOK_INTERITIAL_ID);
        }
        btn_new_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(MainActivity.this,RecyclyeViews.class);
//                startActivity(i);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(MainActivity.this,RecyclyeViews.class);
//                startActivity(i);
                finish();
            }
        });
        AdSettings.addTestDevice("eaf5cb1b-531e-432d-9e78-597e34781447");

        path1 = getIntent().getExtras().getString(Constants.image1);
        Glide.with(this)
                .load(path1)
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .into(img_girl);

        text1.setText(getResources().getString(R.string.click) + " " + Integer.toString(count));
        text2.setText(getResources().getString(R.string.click) + " " + Integer.toString(count2));
        text3.setText(getResources().getString(R.string.click) + " " + Integer.toString(count3));
        text4.setText(getResources().getString(R.string.click) + " " + Integer.toString(count4));
        text5.setText(getResources().getString(R.string.click) + " " + Integer.toString(count5));


    }



    private void load_InterstitialAd_fb() {
        fbinterstitialAd = new com.facebook.ads.InterstitialAd(this, "YOUR_PLACEMENT_ID");
        InterstitialAdListener interstitialAdListener=new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                fbinterstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
    }

    public void setInterstitialAd(){
     interstitialAd = new InterstitialAd(this);

        // set the ad unit ID
     interstitialAd.setAdUnitId(ginurl);

        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
     interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (interstitialAd.isLoaded()) {

                }
            }
        });
 }
    @OnClick({R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.img_new_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image1:
                try {
                    if (coinCount==0)
                    {
                        showDialog1();
                    }
                    isFirst = true;
                    isSecond = false;
                    isThird = false;
                    isFour = false;
                    isFive = false;
                    if (coinCount > 0){
                        count--;
                        coinCount--;
                        coinCountText.setText("Coins: " + coinCount);
                    }
                    CommonFunctions.setPreference(this, Constants.coin, coinCount);
                    if (count <= 0) {
                        startIntent();
                    } else {
                        text1.setText("Click "+Integer.toString(count));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.image2:
                try {
                    if (coinCount==0)
                    {
                        showDialog1();
                    }
                    isFirst = false;
                    isSecond = true;
                    isThird = false;
                    isFour = false;
                    isFive = false;
                    if (coinCount > 0){
                        count2--;
                        coinCount--;
                        coinCountText.setText("Coins: " + coinCount);
                    }
                    CommonFunctions.setPreference(this, Constants.coin, coinCount);
                    if (count2 <= 0) {
                        startIntent();
                    } else {
                        text2.setText("Click "+Integer.toString(count2));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.image3:
                try {
                    if (coinCount==0)
                    {
                        showDialog1();
                    }
                    isFirst = false;
                    isSecond = false;
                    isThird = true;
                    isFour = false;
                    isFive = false;
                    if (coinCount > 0){
                        count3--;
                        coinCount--;
                        coinCountText.setText("Coins: " + coinCount);
                    }
                    CommonFunctions.setPreference(this, Constants.coin, coinCount);
                    if (count3 <= 0) {
                        startIntent();

                    } else {
                        text3.setText("Click "+Integer.toString(count3));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.image4:
                try {
                    if (coinCount==0)
                    {
                        showDialog1();
                    }
                    isFirst = false;
                    isSecond = false;
                    isThird = false;
                    isFour = true;
                    isFive = false;
                    if (coinCount > 0){
                        count4--;
                        coinCount--;
                        coinCountText.setText("Coins: " + coinCount);
                    }
                    CommonFunctions.setPreference(this, Constants.coin, coinCount);
                    if (count4 <= 0) {
                        startIntent();
                    } else {
                        text4.setText("Click "+Integer.toString(count4));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.image5:
                try {
                    if (coinCount==0)
                    {
                        showDialog1();
                    }
                    isFirst = false;
                    isSecond = false;
                    isThird = false;
                    isFour = false;
                    isFive = true;
                    if (coinCount > 0){
                        count5--;
                        coinCount--;
                        coinCountText.setText("Coins: " + coinCount);
                    }
                    CommonFunctions.setPreference(this, Constants.coin, coinCount);
                    if (count5 <= 0) {
                        startIntent();
                    } else {
                        text5.setText("Click "+Integer.toString(count5));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.img_new_image:
                try {
                    if (CommonFunctions.checkConnection(MainActivity.this)) {
                       // loadRewardedAds();
                    } else {
                        startActivity(new Intent(MainActivity.this, ShowImage.class).putExtra(Constants.image2, path2));
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    private void addCoins(int coins) {
        coinCount += coins;
        coinCountText.setText("Coins: " + coinCount);
    }

    private void showDialog1() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Your coin is 0\n" +
                "Click on Show Ads to get 20 coins");
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton("Show Ads", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                googRewardedVideoAdleadshow();
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                googleinterstialadshow();
            }
        });
        adb.show();
    }

    public void googleinterstialadshow(){
        interstitialAd.show();
        setInterstitialAd();
    }
    public void facebookinterstialadshow(){

    }
    public void googRewardedVideoAdleadshow(){
        RewardedAdCallback adCallback = new RewardedAdCallback() {
            @Override
            public void onRewardedAdOpened() {
                // Ad opened.
            }

            @Override
            public void onRewardedAdClosed() {
                // Ad closed.
            }

            @Override
            public void onUserEarnedReward(@NonNull com.google.android.gms.ads.rewarded.RewardItem rewardItem) {
                addCoins(20);
            }
        };
        rewardedAd.show(MainActivity.this,adCallback);
        load_RewardedVideoAd_admob();
    }
    public void facebookRewardedVideoAdadshow(){

    }
    private void load_RewardedVideoAd_admob() {
        rewardedAd = new RewardedAd(this,
                grewardedurl);

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                if (rewardedAd.isLoaded()) {

                } else {
                    Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                }
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    private void loadRewardedVideoAd_fb() {
        fbrewardedVideoAd = new RewardedVideoAd(this, frewardedburl);
        com.facebook.ads.RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                Log.e("TAG", "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.d("TAG", "Rewarded video ad is loaded and ready to be displayed!");
                fbrewardedVideoAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d("TAG", "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.d("TAG", "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                Log.d("TAG", "Rewarded video completed!");
                addCoins(20);
            }

            @Override
            public void onRewardedVideoClosed() {
                Log.d("TAG", "Rewarded video ad closed!");
            }
        };
        fbrewardedVideoAd.loadAd(
                fbrewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }

    private void checkCount() {
        if (count <= 0 && count2 <= 0 && count3 <= 0 && count4 <= 0 && count5 <= 0) {
            btn_new_image.setVisibility(View.VISIBLE);
        }
    }

    private void startIntent() {
        if (isFirst) {
            image1.setVisibility(View.INVISIBLE);
            text1.setVisibility(View.INVISIBLE);
            checkCount();
        } else if (isSecond) {
            image2.setVisibility(View.INVISIBLE);
            text2.setVisibility(View.INVISIBLE);
            checkCount();

        } else if (isThird) {
            image3.setVisibility(View.INVISIBLE);
            text3.setVisibility(View.INVISIBLE);
            checkCount();

        } else if (isFour) {
            image4.setVisibility(View.INVISIBLE);
            text4.setVisibility(View.INVISIBLE);
            checkCount();

        } else if (isFive) {
            image5.setVisibility(View.INVISIBLE);
            text5.setVisibility(View.INVISIBLE);
            checkCount();
        }
    }

    @Override
    protected void onDestroy() {
        if (fbrewardedVideoAd != null) {
            fbrewardedVideoAd.destroy();
            fbrewardedVideoAd = null;
        }
        if (fbinterstitialAd != null) {
            fbinterstitialAd.destroy();
        }
        super.onDestroy();
    }
}