package com.example.beeapp.ui.main;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.beeapp.data.database.BeeDatabase;
import com.example.beeapp.data.model.Bee;

import java.util.List;

public class MainViewModel extends ViewModel {
    private LiveData<List<Bee>> bees;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private BeeDatabase database;

    public void initialize(Application application) {
        try {
            database = BeeDatabase.getInstance(application);
            bees = database.beeDao().getAllBees();
            isLoading.setValue(false);
        } catch (Exception e) {
            e.printStackTrace();
            isLoading.setValue(false);
        }
    }

    public LiveData<List<Bee>> getBees() {
        return bees;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}