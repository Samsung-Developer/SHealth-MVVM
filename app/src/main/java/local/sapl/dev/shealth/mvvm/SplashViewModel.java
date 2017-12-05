package local.sapl.dev.shealth.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.samsung.android.sdk.healthdata.HealthDataStore;

import java.util.List;

/**
 * Created by Owner on 5/12/2017.
 */

public class SplashViewModel extends AndroidViewModel {

    private final MediatorLiveData<HealthDataStore> mObservableDataStore;

    public SplashViewModel(@NonNull Application application) {
        super(application);

        mObservableDataStore = new MediatorLiveData<>();
        mObservableDataStore.setValue(null);

        HealthDataStore ds = new HealthDataStore();
        ds.

        LiveData<HealthDataStore> dataStore = ((SHealth) application).getRepository().getHealthDataStore();

        // observe the changes of the products from the database and forward them
        mObservableDataStore.addSource(dataStore, mObservableDataStore::setValue);
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
            return (T) new SplashViewModel(mApplication);
        }
    }

}
