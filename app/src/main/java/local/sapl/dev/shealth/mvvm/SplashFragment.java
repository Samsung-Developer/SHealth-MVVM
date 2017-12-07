package local.sapl.dev.shealth.mvvm;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import local.sapl.dev.shealth.mvvm.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment {

    static { System.loadLibrary("native-lib"); }
    public native String stringFromJNI();

    public static final String TAG = "ProductListViewModel";

    private SHealth app;
    private TextView tv;

    private FragmentSplashBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        app = (SHealth) this.getActivity().getApplication();
        SplashViewModel.Factory factory = new SplashViewModel.Factory(app);

        final SplashViewModel viewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
        //final SplashViewModel viewModel = ViewModelProviders.of(this).get(SplashViewModel.class);

        subscribeUi(viewModel);

        // Example of a call to a native method
        // tv = findViewById(R.id.loading_tv);
        // tv.setText(stringFromJNI());
    }

    private void subscribeUi(SplashViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getConnectionStatus().observe(
            this,
            new Observer<HealthDSConnectionListener.Status>() {
                @Override
                public void onChanged(@Nullable HealthDSConnectionListener.Status status) {
                    if (status == HealthDSConnectionListener.Status.CONNECTED) {
                        Log.v("statusOnChange", "Connected");
                        mBinding.setIsLoading(true);
                        //mBinding.setConnectionStatus("Connected");
                    } else if(status == HealthDSConnectionListener.Status.DISCONNECTED){
                        Log.v("statusOnChange", "DISCONNECTED");
                        mBinding.setIsLoading(true);
                        //mBinding.setConnectionStatus("DISCONNECTED");
                    }else{
                        Log.v("statusOnChange", "Failed");
                        mBinding.setIsLoading(true);
                        //mBinding.setConnectionStatus("Failed");
                    }
                    // espresso does not know how to wait for data binding's loop so we execute changes sync.
                    mBinding.executePendingBindings();
                }
            }
        );
    }

}