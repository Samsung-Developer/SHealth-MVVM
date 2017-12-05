package local.sapl.dev.shealth.mvvm;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SHealth app;
    private ActivityMainBinding mainActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel.Factory factory = new MainViewModel.Factory(app);
        final MainViewModel model = ViewModelProviders.of(this, factory).get(MainViewModel.class);

        mainActivityBinding.setMainViewModel(model);
        subscribeToModel(model);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void subscribeToModel(final MainViewModel model) {

        // Observe product data
        model.getObservableUser().observe(this, new Observer<UserProfileEntity>() {
            @Override
            public void onChanged(@Nullable UserProfileEntity userEntity) {
                model.setHealthData(userEntity);
            }
        });
    }

}
