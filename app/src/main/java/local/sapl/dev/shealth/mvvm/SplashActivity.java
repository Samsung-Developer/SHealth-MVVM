package local.sapl.dev.shealth.mvvm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            SplashFragment fragment = new SplashFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, SplashFragment.TAG).commit();
        }
    }

}
