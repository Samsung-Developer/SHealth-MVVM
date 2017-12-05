package local.sapl.dev.shealth.mvvm;

import android.arch.lifecycle.Lifecycle;
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

    private ProductAdapter mProductAdapter;

    private FragmentSplashBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);

        mProductAdapter = new ProductAdapter(mProductClickCallback);
        mBinding.productsList.setAdapter(mProductAdapter);

        return mBinding.getRoot();

        splashActivityBinding.setHealthData(model);
        subscribeToModel(model);

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
        tv = findViewById(R.id.loading_tv);
        tv.setText(stringFromJNI());
    }

    private void subscribeUi(SplashViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> myProducts) {
                if (myProducts != null) {
                    mBinding.setIsLoading(false);
                    mProductAdapter.setProductList(myProducts);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }
        });
    }

    private final ProductClickCallback mProductClickCallback = new ProductClickCallback() {
        @Override
        public void onClick(Product product) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((SplashActivity) getActivity()).show(product);
            }
        }
    };

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