package eu.hackathonovo.device;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import eu.hackathonovo.R;
import eu.hackathonovo.ui.home_rescuer.HomeRescuerActivity;

public class HackatonovoFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = HackatonovoFirebaseMessagingService.class.getSimpleName();

    public static final String TEXT_EXTRA = "TEXT";

    public static final String TITLE_EXTRA = "TITLE";

    public static final String SURVEY_EXTRA = "SURVEY";

    public static final String Notification = "Notification";

    Random rand = new Random();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "FROM:" + remoteMessage.getFrom());
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            try {
                //JSONObject json = new JSONObject(recivedMsg); //.replace("\\", ""));

                sendNotification(remoteMessage.getData().get("text"), remoteMessage.getData().get("title"), remoteMessage.getData().get("survey"));
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data: " + remoteMessage.getData());
            try {
                //JSONObject json = new JSONObject(recivedMsg); //.replace("\\", ""));

                sendNotification(remoteMessage.getData().get("text"), remoteMessage.getData().get("title"), remoteMessage.getData().get("survey"));
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

        }
    }

    /**
     * Dispay the notification
     */
    private void sendNotification(String body, String title, String survey) {
        int randomNum = rand.nextInt((9999999 - 1) + 1) + 1;

        Intent intent = new Intent(this, HomeRescuerActivity.class).putExtra(TEXT_EXTRA, body).putExtra(TITLE_EXTRA, title).putExtra(SURVEY_EXTRA, survey).putExtra(Notification,"da");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, randomNum, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(randomNum, notifiBuilder.build());
    }
}