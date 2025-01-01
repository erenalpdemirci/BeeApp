package com.example.beeapp.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.beeapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    public static final String EXTRA_BEE_ID = "extra_bee_id";

    private DetailViewModel viewModel;
    private YouTubePlayerView youtubePlayerView;
    private TextView nameTextView;
    private TextView typeTextView;
    private TextView jobTextView;
    private TextView featuresTextView;
    private TextView descriptionTextView;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_detail);

            initializeViews();
            setupToolbar();
            setupViewModel();
            observeViewModel();
        } catch (Exception e) {
            Log.e(TAG, "onCreate hatası: ", e);
            Toast.makeText(this, "Bir hata oluştu: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initializeViews() {
        youtubePlayerView = findViewById(R.id.youtubePlayerView);
        nameTextView = findViewById(R.id.nameTextView);
        typeTextView = findViewById(R.id.typeTextView);
        jobTextView = findViewById(R.id.jobTextView);
        featuresTextView = findViewById(R.id.featuresTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setupToolbar() {
        try {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("Arı Detayı");
            }
        } catch (Exception e) {
            Log.e(TAG, "setupToolbar hatası: ", e);
        }
    }

    private void setupViewModel() {
        try {
            viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
            int beeId = getIntent().getIntExtra(EXTRA_BEE_ID, -1);
            Log.d(TAG, "Alınan beeId: " + beeId);
            if (beeId != -1) {
                viewModel.initialize(getApplication(), beeId);
            } else {
                Log.e(TAG, "Geçersiz beeId: -1");
                Toast.makeText(this, "Geçersiz arı ID'si", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "setupViewModel hatası: ", e);
            Toast.makeText(this, "ViewModel başlatılamadı", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void observeViewModel() {
        try {
            viewModel.getBee().observe(this, bee -> {
                if (bee != null) {
                    try {
                        Log.d(TAG, "Arı verisi alındı: " + bee.getName());
                        nameTextView.setText(bee.getName());
                        typeTextView.setText(bee.getType());
                        jobTextView.setText(bee.getJob());
                        featuresTextView.setText(bee.getFeatures());
                        descriptionTextView.setText(bee.getDescription());

                        getLifecycle().addObserver(youtubePlayerView);
                        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                String videoId = bee.getYoutubeVideoId();
                                Log.d(TAG, "YouTube Video ID: " + videoId);
                                if (videoId != null && !videoId.isEmpty()) {
                                    youTubePlayer.cueVideo(videoId, 0);
                                }
                            }
                        });

                        progressBar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Log.e(TAG, "Arı verisi görüntüleme hatası: ", e);
                        Toast.makeText(DetailActivity.this, "Veriler görüntülenemedi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Arı verisi null");
                    Toast.makeText(this, "Arı bilgileri bulunamadı", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            viewModel.getIsLoading().observe(this, isLoading -> {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            });
        } catch (Exception e) {
            Log.e(TAG, "observeViewModel hatası: ", e);
            Toast.makeText(this, "Veri gözlemlenirken hata oluştu", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (youtubePlayerView != null) {
                youtubePlayerView.release();
            }
        } catch (Exception e) {
            Log.e(TAG, "onDestroy hatası: ", e);
        }
    }
}