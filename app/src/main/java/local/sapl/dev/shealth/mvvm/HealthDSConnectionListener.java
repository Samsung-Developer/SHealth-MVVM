package local.sapl.dev.shealth.mvvm;

import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthDataStore;

/**
 * Created by Owner on 6/12/2017.
 */

public class HealthDSConnectionListener implements HealthDataStore.ConnectionListener {

    public enum Status{
        CONNECTED(1), DISCONNECTED(2), FAILED(0);
        private final int index;
        Status(int index) { this.index = index; }
        int getIndex() { return this.index; }
    }

    private final HealthRepository repo;
    public HealthDSConnectionListener(HealthRepository repo) { this.repo = repo; }

    @Override public void onConnected() { repo.setDSConnectionStatus(Status.CONNECTED); }
    @Override public void onDisconnected() { repo.setDSConnectionStatus(Status.DISCONNECTED); }
    @Override public void onConnectionFailed(HealthConnectionErrorResult healthConnectionErrorResult) {
        repo.setDSConnectionStatus(Status.FAILED);
    }
}