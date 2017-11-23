package vn.com.jvit.musicdemoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by User on 11/23/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkChangeReceiver";
    private boolean mFlag = false;


    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetWorkUtils.getConnectivityStatusString(context);
        if (intent != null && intent.getAction() == Constants.CONNECTIVITY_CHANGE) {
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
            if (NetWorkUtils.getConnectivityStatus(context) == 1 || NetWorkUtils.getConnectivityStatus(context) == 2) {
                NetworkChangeListener listener = MainActivity.sListener;
                if (listener != null) listener.onRetryDownload();
            }
        }
    }

    public interface NetworkChangeListener {
        void onRetryDownload();
    }
}
