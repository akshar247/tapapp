package com.touchgirl.touchgirl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.touchgirl.touchgirl.R;
import com.touchgirl.touchgirl.SplashScreen;
import com.touchgirl.touchgirl.adapter.PersonAdapter;
import com.touchgirl.touchgirl.config.CommonFunctions;
import com.touchgirl.touchgirl.config.Constants;
import com.touchgirl.touchgirl.config.TapAppConfig;
import com.touchgirl.touchgirl.model.ModelClass;
import com.touchgirl.touchgirl.model.PersonResponse;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclyeViews extends AppCompatActivity {
    @BindView(R.id.rv_add_survey)
    RecyclerView rv_favorites_item;
    PersonResponse homeData;
    //PersonAdapter favouriteAdapter;
    List<PersonResponse.Data> dataEntity = new ArrayList<>();
    private AdView mAdView;
    com.facebook.ads.AdView fbAdView;
    AdRequest adRequest;
    View adContainer;
    InterstitialAd mInterstitialAd;
    FirebaseDatabase database;
    DatabaseReference myref;
    String ginurl,finurl,gburl,fburl,grewardedurl,frewardedburl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclye_view);
        adContainer = findViewById(R.id.adView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdSettings.addTestDevice("eaf5cb1b-531e-432d-9e78-597e34781447");

        AudienceNetworkAds.initialize(this);
        Intent intent = getIntent();
        ginurl= intent.getStringExtra(Constants.GOOGLE_INTERITIAL_ID);
        gburl= intent.getStringExtra(Constants.GOOGLE_BANNER_ID);
        finurl= intent.getStringExtra(Constants.FACEBOOK_INTERITIAL_ID);
        fburl= intent.getStringExtra(Constants.FACEBOOK_BANNER_ID);
        grewardedurl= intent.getStringExtra(Constants.GOOGLE_REWARDED_ID);
        frewardedburl= intent.getStringExtra(Constants.FACEBOOK_REWARDED_ID);

//        mInterstitialAd = new InterstitialAd(this);
//
//        // set the ad unit ID
//        mInterstitialAd.setAdUnitId(unitid);
//
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//
//        // Load ads into Interstitial Ads
//        mInterstitialAd.loadAd(adRequest);
//        load_bannerAd_admob();
//        mInterstitialAd.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                showInterstitial();
//            }
//        });
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.LARGE_BANNER);
        loadBannerAds();
        init();
    }
    public void loadBannerAds() {
        if (SplashScreen.appConfigModel.IS_GOOGLE.equalsIgnoreCase(Constants.VAL_TRUE)) {
            load_bannerAd_admob();
        } else {
            load_bannerAd_fb();
        }
    }
    private void load_bannerAd_admob() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(gburl);
        AdRequest adRequest = new AdRequest.Builder().build();
        ((RelativeLayout) adContainer).addView(mAdView);
        mAdView.loadAd(adRequest);

    }

    private void load_bannerAd_fb() {
        fbAdView = new com.facebook.ads.AdView(RecyclyeViews.this, fburl, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        ((RelativeLayout) adContainer).addView(fbAdView);
        com.facebook.ads.AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e("TAG", "onError-------- " + ad + "   " + adError);

                Toast.makeText(
                        RecyclyeViews.this,
                        "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.e("TAG", "onAdLoaded-------- " + ad);
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e("TAG", "onAdClicked-------- " + ad);
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e("TAG", "onLoggingImpression-------- " + ad);
                // Ad impression logged callback
            }
        };
        fbAdView.loadAd(fbAdView.buildLoadAdConfig().withAdListener(adListener).build());

    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void init() {
        try {
            ButterKnife.bind(this);
            AudienceNetworkAds.initialize(this);
            AdSettings.addTestDevice(Constants.facebook_HashKey);
            rv_favorites_item=(RecyclerView)findViewById(R.id.rv_add_survey);
            rv_favorites_item.setHasFixedSize(true);
            rv_favorites_item.setLayoutManager(new LinearLayoutManager(this));
            database=FirebaseDatabase.getInstance();
            myref=database.getReference("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ModelClass, BlogViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ModelClass, BlogViewHolder>(
                        ModelClass.class,
                        R.layout.item,
                        BlogViewHolder.class,
                        myref) {

                    @Override
                    protected void populateViewHolder(BlogViewHolder viewHolder, ModelClass model,int position){
                        ImageView post_image=(ImageView)findViewById(R.id.iv_profile);
                        viewHolder.setTitle(model.getTitle());
                       viewHolder.setImage(getApplicationContext(),model.getImage());
//                        Glide.with(RecyclyeViews.this)
//                                .load(model.getImage())
//                                .error(R.drawable.no_image)
//                                .into(post_image);
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent1 = new Intent (RecyclyeViews.this, MainActivity.class);
                                Bundle data1 = new Bundle();
                                data1.putString(Constants.image1,model.getImage());
                                data1.putString(Constants.GOOGLE_REWARDED_ID, grewardedurl );
                                data1.putString(Constants.FACEBOOK_REWARDED_ID, frewardedburl);
                                data1.putString(Constants.GOOGLE_INTERITIAL_ID, ginurl);
                                data1.putString(Constants.FACEBOOK_INTERITIAL_ID, finurl);
                                intent1.putExtras(data1);
                                startActivity(intent1);
                            }
                        });

                    }
                };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        rv_favorites_item.setLayoutManager(gridLayoutManager);
        rv_favorites_item.setAdapter(firebaseRecyclerAdapter);

    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public BlogViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        public void setTitle(String title){
            TextView post_title=(TextView)mView.findViewById(R.id.tv_person_name);
            post_title.setText(title);

        }
        public void setImage(Context ctx, String image){
            ImageView post_image=(ImageView) mView.findViewById(R.id.iv_profile);
            //      Picasso.with(ctx).load(image).into(post_image);

            Picasso.get().load(image).into(post_image);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.errorOf(R.drawable.no_image))
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(ctx)
                    .load(image)
                    .apply(requestOptions)
                    .into(post_image);
        }
    }
}
