package local.sapl.dev.shealth.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

/**
 * Created by andre.erwanto on 5/12/17.
 */

public class MainViewModel extends AndroidViewModel {

    private final LiveData<UserProfileEntity> mObservableUser;

    public ObservableField<UserProfileEntity> user = new ObservableField<>();

    public MainViewModel(@NonNull Application application, DataRepository repository) {
        super(application);
        mObservableUser = repository.loadProfile(ProfileType.GOOGLE);
    }

    public LiveData<UserProfileEntity> getObservableUser() { return mObservableUser; }

    public void setUser(UserProfileEntity user) { this.user.set(user); }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;
        private final DataRepository mRepository;

        public Factory(@NonNull Application application) {
            mApplication = application;
            mRepository = ((SHealth) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new MainViewModel(mApplication, mRepository);
        }
    }

}
