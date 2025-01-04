package com.example.fables.ui.spalsh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fables.MainActivity;
import com.example.fables.R;
import com.example.fables.ui.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Trouver le logo
        ImageView splashLogo = findViewById(R.id.splash_logo);

        // Charger l'animation
        Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Appliquer l'animation au logo
        splashLogo.startAnimation(bounceAnimation);

        ProgressBar progressBar = findViewById(R.id.progress_bar);
        int maxProgress = 100;
        final int[] progress = {0};

        // Simuler une progression
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (progress[0] < maxProgress) {
                    progress[0] += 10; // Incrémente la progression
                    progressBar.setProgress(progress[0]);
                    handler.postDelayed(this, 1000); // Refaire après 1s
                } else {
                    // Passer à l'activité principale après la fin de la progression
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        handler.post(runnable);
    }
}