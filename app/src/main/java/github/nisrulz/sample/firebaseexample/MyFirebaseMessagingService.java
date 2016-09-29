package github.nisrulz.sample.firebaseexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
  private static final String TAG = "FCM";

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    // ...

    // TODO(developer): Handle FCM messages here.
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    Log.d(TAG, "From: " + remoteMessage.getFrom());

    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      Log.d(TAG, "Message data payload: " + remoteMessage.getData());
    }

    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
      String msg = "Message Notification Body: " + remoteMessage.getNotification().getBody();
      Log.d(TAG, msg);
    }

    // Also if you intend on generating your own notifications as a result of a received FCM
    // message, here is where that should be initiated.

    showNotification(remoteMessage.getNotification().getBody(), remoteMessage.getFrom());
  }

  private void showNotification(String body, String title) {
    final Intent emptyIntent = new Intent();
    PendingIntent pendingIntent =
        PendingIntent.getActivity(this, 1, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent); //Required on Gingerbread and below

    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(2, mBuilder.build());
  }
}
