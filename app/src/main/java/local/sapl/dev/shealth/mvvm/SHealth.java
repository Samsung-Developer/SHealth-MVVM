package local.sapl.dev.shealth.mvvm;

import android.app.Application;
import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;

import java.util.Collections;
import java.util.Map;

public class SHealth extends Application {

    public static final String APP_TAG = "SimpleHealth";
    private HealthDataStore mStore;

    public HealthDataService getHealthService() { return HealthService.getInstance(); }

    public HealthRepository getRepository() { return HealthRepository.getInstance(getHealthService(), this); }
}
