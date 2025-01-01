package com.example.beeapp.ui.detail;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.beeapp.data.database.BeeDatabase;
import com.example.beeapp.data.model.Bee;

public class DetailViewModel extends ViewModel {
    private static final String TAG = "DetailViewModel";
    private LiveData<Bee> bee;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private BeeDatabase database;

    public void initialize(Application application, int beeId) {
        try {
            Log.d(TAG, "initialize çağrıldı, beeId: " + beeId);
            database = BeeDatabase.getInstance(application);
            bee = database.beeDao().getBeeById(beeId);
            isLoading.setValue(false);
            Log.d(TAG, "initialize başarılı");
        } catch (Exception e) {
            Log.e(TAG, "initialize hatası: ", e);
            isLoading.setValue(false);
        }
    }

    public LiveData<Bee> getBee() {
        return bee;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}