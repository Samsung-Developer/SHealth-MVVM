package local.sapl.dev.shealth.mvvm;

import android.content.Context;

import com.samsung.android.sdk.healthdata.HealthDataService;

public abstract class HealthService {

    private static HealthDataService sInstance;
    public static HealthDataService getInstance() {
        if (sInstance == null) {
            synchronized (HealthService.class) {
                if (sInstance == null) {
                    sInstance = new HealthDataService();
                    return sInstance;
                    //buildService(context.getApplicationContext());
                    //sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

}
