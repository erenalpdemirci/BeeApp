package com.example.beeapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beeapp.R;
import com.example.beeapp.databinding.ActivitySplashBinding;
import com.example.beeapp.ui.main.MainActivity;
import com.example.beeapp.utils.NetworkUtils;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private static final long SPLASH_DELAY = 3000; // 3 saniye
    private boolean isNetworkChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Logo animasyonunu başlat
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        binding.logoImageView.startAnimation(rotateAnimation);

        // Animasyon bittiğinde internet kontrolü yap
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isNetworkChecked) {
                    checkNetworkAndProceed();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (!isNetworkChecked) {
                    checkNetworkAndProceed();
                }
            }
        });
    }

    private void checkNetworkAndProceed() {
        isNetworkChecked = true;
        binding.statusTextView.setText("İnternet bağlantısı kontrol ediliyor...");

        if (NetworkUtils.isInternetAvailable(this)) {
            binding.statusTextView.setText("İnternet bağlantısı başarılı!");
            // Kısa bir gecikme ile ana ekrana geç
            mainHandler.postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }, 1000);
        } else {
            binding.statusTextView.setText("İnternet bağlantısı yok!");
            Toast.makeText(this, "Lütfen internet bağlantınızı kontrol edin!", Toast.LENGTH_LONG).show();
            // 3 saniye sonra tekrar dene
            mainHandler.postDelayed(() -> {
                isNetworkChecked = false;
                binding.logoImageView.startAnimation(
                        AnimationUtils.loadAnimation(this, R.anim.rotate_animation)
                );
            }, SPLASH_DELAY);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainHandler.removeCallbacksAndMessages(null);
        binding = null;
    }
}