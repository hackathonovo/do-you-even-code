package eu.hackathonovo.device;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class HackathonovoFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = HackathonovoFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        //Get updated token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "New Token: " + refreshedToken);

        //You can save the token into third party server to do anything you want
    }
}