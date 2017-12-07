package local.sapl.dev.shealth.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;

import java.util.List;

public class HealthRepository {

    private static HealthRepository sHealthInstance;
    private final HealthDataService mService;
    private HealthDataStore mStore;
    private SHealth app;

    private MutableLiveData<HealthDSConnectionListener.Status> DSConnectionStatus;
    public MutableLiveData<HealthDSConnectionListener.Status> getDSConnectionStatus(){
        if (DSConnectionStatus == null) { DSConnectionStatus = new MutableLiveData<HealthDSConnectionListener.Status>(); }
        return DSConnectionStatus;
    }
    public void setDSConnectionStatus(HealthDSConnectionListener.Status status){ getDSConnectionStatus().setValue(status); }
    private MediatorLiveData<HealthDSConnectionListener.Status> mObservableConnectionStatus;

    private HealthRepository(final HealthDataService service, final SHealth context) {
        this.app = context;
        mService = service;

        try { mService.initialize(app); }
        catch (Exception e) { e.printStackTrace(); }

        mObservableConnectionStatus = new MediatorLiveData<>();
        mObservableConnectionStatus.setValue(null);
        mObservableConnectionStatus.addSource(DSConnectionStatus, mObservableConnectionStatus::setValue);
    }

    public void connectDataStore(HealthDSConnectionListener listener) {
        if(mStore == null) {
            mStore = new HealthDataStore(app, listener);
            mStore.connectService();
        }
    }

    public static HealthRepository getInstance(final HealthDataService service, final Context context) {
        if (sHealthInstance == null) {
            synchronized (HealthRepository.class) {
                if (sHealthInstance == null) {
                    sHealthInstance = new HealthRepository(service, (SHealth) context);
                }
            }
        }
        return sHealthInstance;
    }

    public LiveData<HealthDSConnectionListener.Status> getConnectionStatus() { return mObservableConnectionStatus; }
}