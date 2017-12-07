package local.sapl.dev.shealth.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthDataStore;

import java.util.List;

/**
 * Created by Owner on 5/12/2017.
 */

public class SplashViewModel extends AndroidViewModel {

    private final MediatorLiveData<HealthDataStore> mObservableDataStore;
    private final HealthDSConnectionListener mConnectionListener;

    public SplashViewModel(@NonNull Application application, HealthRepository repository) {
        super(application);
// do i need to put repo in class property??
        mObservableDataStore = new MediatorLiveData<>();
        mObservableDataStore.setValue(null);

        LiveData<HealthDataStore> dataStore = repository.getHealthDataStore();

        // observe the changes of the products from the database and forward them
        mObservableDataStore.addSource(dataStore, mObservableDataStore::setValue);

        mConnectionListener = new HealthDSConnectionListener(repository){
            @Override
            public void onConnected() {
                super.onConnected();

            }

            @Override
            public void onConnectionFailed(HealthConnectionErrorResult error) {
                super.onConnectionFailed(error);

            }

            @Override
            public void onDisconnected() {
                super.onDisconnected();

            }
        };

    }

    public LiveData<String> getHealthSDKConnectionStatus(mConnectionListener){

    }



    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final HealthRepository mRepository;

        public Factory(@NonNull Application application) {
            mApplication = application;
            mRepository = ((SHealth) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SplashViewModel(mApplication, mRepository);
        }
    }

}
