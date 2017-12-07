package local.sapl.dev.shealth.mvvm;

import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthDataStore;

/**
 * Created by Owner on 6/12/2017.
 */

public class HealthDSConnectionListener implements HealthDataStore.ConnectionListener {
    private final HealthRepository repo;

    public HealthDSConnectionListener(HealthRepository repo) { this.repo = repo; }

    @Override public void onConnected() { repo.setDSConnectionStatus("Connected"); }
    @Override public void onDisconnected() { repo.setDSConnectionStatus("Disconnected"); }
    @Override public void onConnectionFailed(HealthConnectionErrorResult healthConnectionErrorResult) {
        repo.setDSConnectionStatus("Connection Failed");
    }
}