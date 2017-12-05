package local.sapl.dev.shealth.mvvm;

import android.content.Context;

import com.samsung.android.sdk.healthdata.HealthDataService;

public abstract class HealthService {

    private static HealthDataService sInstance;

    public static boolean getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (HealthService.class) {
                if (sInstance == null) {
                    return buildService(context.getApplicationContext());
                    //sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return true;
    }

    private static boolean buildService(Context applicationContext) {
        HealthDataService healthDataService = new HealthDataService();
        try {
            healthDataService.initialize(applicationContext);
            sInstance = healthDataService;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //return healthDataService;
        // Create a HealthDataStore instance and set its listener
        //mStore = new HealthDataStore(applicationContext, listener);
        // Request the connection to the health data store
        //mStore.connectService();

    }

}
