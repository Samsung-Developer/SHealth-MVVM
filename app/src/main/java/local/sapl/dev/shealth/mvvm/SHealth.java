package local.sapl.dev.shealth.mvvm;

import android.app.Application;

/**
 * Created by andre.erwanto on 5/12/17.
 */

public class SHealth extends Application {

    public AppDatabase getDatabase() { return AppDatabase.getInstance(this, mAppExecutors); }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }

}
