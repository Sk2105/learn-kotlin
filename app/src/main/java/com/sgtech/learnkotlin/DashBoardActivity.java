package com.sgtech.learnkotlin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.net.ConnectivityManager;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Objects;

public class DashBoardActivity extends AppCompatActivity {
    TextView t1, t2, t3;
    WebView img1, img2, img3;
    int id;
    CardView card1, card2, card3;
    Dialog dialog;
    AdView adview;
    AdRequest adRequest;
    RewardedInterstitialAd ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        MobileAds.initialize(this);
        adRequest = new AdRequest.Builder().build();
        id = getIntent().getIntExtra(KeyClass.FIRE_ID, 1);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        findId();
        reload();
        loadBanner();
    }

    private void loadBanner() {
        adview.loadAd(adRequest);
        new Handler().postDelayed(this::loadAd, 4000);
    }

    @Override
    public void onBackPressed() {
        try {
            showAd();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private void findId() {
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        adview = findViewById(R.id.adView);
    }

    private void loadData() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(KeyClass.COLLECTION_ID).document(String.valueOf(id)).get().addOnCompleteListener(this::loadFireData);
    }

    private void loadFireData(Task<DocumentSnapshot> task) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_TITLE)).toString());
        t1.setText(Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_T1)).toString());
        if (!Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_T2)).toString().equals("")) {
            t2.setText(Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_T2)).toString());
            if (!Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_T3)).toString().equals("")) {
                t3.setText(Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_T3)).toString());
            }
        }
        if (!Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_IMG1)).toString().equals("")) {
            card1.setVisibility(View.VISIBLE);
            img1.loadUrl(Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_IMG1)).toString());
            if (!Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_IMG2)).toString().equals("")) {
                card2.setVisibility(View.VISIBLE);
                img2.loadUrl(Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_IMG2)).toString());
                if (!Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_IMG3)).toString().equals("")) {
                    card3.setVisibility(View.VISIBLE);
                    img3.loadUrl(Objects.requireNonNull(task.getResult().get(KeyClass.FIRE_IMG3)).toString());
                }
            }
        }
    }

    private boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    private void reload() {
        if (!checkConnection()) {
            showDailog();
        } else {
            loadData();
        }
    }

    private void showDailog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.connection_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(android.R.color.transparent));
        Button btn = dialog.findViewById(R.id.reload);
        dialog.setCancelable(false);
        btn.setOnClickListener(v -> {
            dialog.dismiss();
            reload();
        });
        dialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                dialog.dismiss();
                onBackPressed();
            }
            return true;
        });
        dialog.create();
        dialog.show();

    }

    private void loadAd() {
        RewardedInterstitialAd.load(this, KeyClass.REWARDED_ADS_ID, adRequest, new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                ad = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                super.onAdLoaded(rewardedInterstitialAd);
                ad = rewardedInterstitialAd;
                ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }
                });
            }
        });

    }

    public void showAd() {
        if (ad != null) {
            ad.show(DashBoardActivity.this, rewardItem -> {

            });
        }


    }

}