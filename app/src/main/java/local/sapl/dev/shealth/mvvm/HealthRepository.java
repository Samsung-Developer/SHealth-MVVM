package local.sapl.dev.shealth.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;

import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;

import java.util.List;

public class HealthRepository {

    private static HealthRepository sHealthInstance;
    private final HealthDataService mService;
    private SHealth app;

    private HealthRepository(final HealthDataService service, final SHealth context) {
        this.app = context;
        mService = service;

        try {
            mService.initialize(app);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<HealthDataStore> getHealthDataStore() { }

    public static HealthRepository getInstance(final HealthDataService service, final Context context) {
        if (sHealthInstance == null) {
            synchronized (DataRepository.class) {
                if (sHealthInstance == null) {
                    sHealthInstance = new HealthRepository(service, (SHealth) context);
                }
            }
        }
        return sHealthInstance;
    }
}