package local.sapl.dev.shealth.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.samsung.android.sdk.healthdata.HealthDataService;

import java.util.List;

/**
 * Created by Owner on 5/12/2017.
 */

public class DataRepository {

    private static HealthDataService sHealthInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<UserProfileEntity>> mObservableUsers;

    private DataRepository(final HealthDataService healthDataService) {
        mDatabase = healthDataService;
        mObservableUsers = new MediatorLiveData<>();

        mObservableUsers.addSource(mDatabase.userDao().loadAllProfiles(),
                userEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableUsers.postValue(userEntities);
                    }
                });
    }

    public static HealthDataService getHealthDataService(final HealthDataService healthDataService) {
        if (sHealthInstance == null) {
            synchronized (HealthDataService.class) {
                if (sHealthInstance == null) {
                    sHealthInstance = new DataRepository(healthDataService);
                }
            }
        }
        return sInstance;
    }

    public LiveData<UserProfileEntity> loadProfile(ProfileType type) { return mDatabase.userDao().loadProfile(type); }
}