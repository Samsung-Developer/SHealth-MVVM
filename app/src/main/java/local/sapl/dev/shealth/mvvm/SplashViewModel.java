package local.sapl.dev.shealth.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Owner on 5/12/2017.
 */

public class SplashViewModel extends AndroidViewModel {

    public SplashViewModel(@NonNull Application application, HealthRepository repository) {
        super(application);
        mObservableUser = repository.loadProfile(ProfileType.GOOGLE);
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
