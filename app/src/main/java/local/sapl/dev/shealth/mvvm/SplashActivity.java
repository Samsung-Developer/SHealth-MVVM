package local.sapl.dev.shealth.mvvm;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthDataStore;

import local.sapl.dev.shealth.mvvm.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private SHealth app;
    private TextView tv;

    private ActivitySplashBinding splashActivityBinding;

    // Used to load the 'native-lib' library on application startup.
    static { System.loadLibrary("native-lib"); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        app = (SHealth) this.getApplication();

        splashActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SplashViewModel.Factory factory = new SplashViewModel.Factory(app);
        final SplashViewModel model = ViewModelProviders.of(this, factory).get(SplashViewModel.class);

        splashActivityBinding.setHealthData(model);
        subscribeToModel(model);

        // Example of a call to a native method
        tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onStart() {
        super.onStart();
        if(!app.loadHealthService()){
            tv.setText("LOAD SERVICE FAILED");
        }else{
            //goto next activity
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    private final HealthDataStore.ConnectionListener mConnectionListener = new HealthDataStore.ConnectionListener() {

        @Override
        public void onConnected() {
            Log.d(APP_TAG, "Health data service is connected.");
            mReporter = new StepCountReporter(mStore);
            if (isPermissionAcquired()) {
                mReporter.start(mStepCountObserver);
            } else {
                requestPermission();
            }
        }

        @Override
        public void onConnectionFailed(HealthConnectionErrorResult error) {
            Log.d(APP_TAG, "Health data service is not available.");
            showConnectionFailureDialog(error);
        }

        @Override
        public void onDisconnected() {
            Log.d(APP_TAG, "Health data service is disconnected.");
            if (!isFinishing()) {
                mStore.connectService();
            }
        }
    };

}
