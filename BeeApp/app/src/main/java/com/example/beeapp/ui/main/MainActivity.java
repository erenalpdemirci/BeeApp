package com.example.beeapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.beeapp.databinding.ActivityMainBinding;
import com.example.beeapp.data.model.Bee;
import com.example.beeapp.ui.detail.DetailActivity;

public class MainActivity extends AppCompatActivity implements BeeAdapter.OnBeeClickListener {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private BeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        setupViewModel();
        observeViewModel();
    }

    private void setupRecyclerView() {
        adapter = new BeeAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.initialize(getApplication());
    }

    private void observeViewModel() {
        viewModel.getBees().observe(this, bees -> {
            if (bees != null) {
                adapter.submitList(bees);
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onBeeClick(Bee bee) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_BEE_ID, bee.getId());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}