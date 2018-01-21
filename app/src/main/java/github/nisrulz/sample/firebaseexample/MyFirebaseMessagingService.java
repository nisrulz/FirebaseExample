package github.nisrulz.sample.firebaseexample;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
  private static final String TAG = "FCM";

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

    if (remoteMessage != null) {
      String fromStr = remoteMessage.getFrom();
      Map<String, String> payloadMap = remoteMessage.getData();
      String bodyStr = "NA";

      // From String
      if (fromStr != null) {
        Log.d(TAG, "From: " + fromStr);
      }

      // Check if message contains a data payload.
      if (payloadMap.size() > 0) {
        Log.d(TAG, "Payload: ");
        for (String key : payloadMap.keySet()) {
          Log.d(TAG, "Key: " + key + ", Value: " + payloadMap.get(key));
        }
      }

      // Check if message contains a notification payload.
      if (remoteMessage.getNotification() != null) {
        bodyStr = remoteMessage.getNotification().getBody();
        Log.d(TAG, "Body: " + bodyStr);
      }

      // Also if you intend on generating your own notifications as a result of a received FCM
      // message, here is where that should be initiated.
      NotificationUtils notificationUtils = new NotificationUtils(this);
      notificationUtils.sendAndroidChannelNotification("From: " + fromStr, bodyStr, 101);
    }


  }
}
