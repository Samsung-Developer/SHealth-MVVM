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

    private final MediatorLiveData<HealthDSConnectionListener.Status> mObservableStatus;
    private final HealthDSConnectionListener mConnectionListener;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        HealthRepository repository = ((SHealth) application).getRepository();

        mObservableStatus = new MediatorLiveData<>();
        mObservableStatus.setValue(null);

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

        repository.connectDataStore(mConnectionListener);
        LiveData<HealthDSConnectionListener.Status> status = repository.getConnectionStatus();
        mObservableStatus.addSource(status, mObservableStatus::setValue);

    }

    public LiveData<HealthDSConnectionListener.Status> getConnectionStatus(){ return mObservableStatus; }

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
