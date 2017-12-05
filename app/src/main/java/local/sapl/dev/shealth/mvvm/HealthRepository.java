package local.sapl.dev.shealth.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;

import java.util.List;

/**
 * Created by Owner on 5/12/2017.
 */

public class HealthRepository {

    private static HealthRepository sHealthInstance;

    private final HealthDataStore mHealthStore;

    private MediatorLiveData<List<UserProfileEntity>> mObservableUsers;

    private HealthRepository(final HealthDataStore healthDataStore) {
        mHealthStore = healthDataStore;

        mObservableUsers = new MediatorLiveData<>();
        mObservableUsers.addSource(mHealthStore.userDao().loadAllProfiles(),
                userEntities -> {
                    if (mHealthStore.getDatabaseCreated().getValue() != null) {
                        mObservableUsers.postValue(userEntities);
                    }
                });
    }

    public static HealthRepository getHealthDataStore(final HealthDataStore healthDataStore) {
        if (sHealthInstance == null) {
            synchronized (HealthDataService.class) {
                if (sHealthInstance == null) {
                    sHealthInstance = new HealthRepository(healthDataStore);
                }
            }
        }
        return sHealthInstance;
    }

    public LiveData<UserProfileEntity> loadProfile(ProfileType type) { return mDatabase.userDao().loadProfile(type); }
}