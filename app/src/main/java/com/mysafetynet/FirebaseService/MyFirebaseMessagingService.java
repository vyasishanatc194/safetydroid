package com.mysafetynet.FirebaseService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonSyntaxException;
import com.mysafetynet.R;
import com.mysafetynet.activities.ChildTabActivity;
import com.mysafetynet.activities.ParentTabActivity;
import com.mysafetynet.network.ApiConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            handleNow();


        }


        if (remoteMessage.getNotification() != null) {
            try {
                JSONObject model = new JSONObject(remoteMessage.getNotification().getBody());

                String title = model.has(ApiConstants.TAGS.title) ? model.getString(ApiConstants.TAGS.title) : "";
                String message = model.has(ApiConstants.TAGS.message) ? model.getString(ApiConstants.TAGS.message) : "";
                Log.d(TAG, "Message Notification Body: " + model.toString());
                sendNotification(model, title, message);
            } catch (JsonSyntaxException | JSONException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }


    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    private void sendNotification(JSONObject model, String title, String messageBody) {
        if (model.has(ApiConstants.TAGS.target_screen)) {
            try {
                String target_screen = model.getString(ApiConstants.TAGS.target_screen);
                Intent intent = null;
                if (target_screen.equalsIgnoreCase("child")) {
                    intent = new Intent(this, ChildTabActivity.class);
                } else if (target_screen.equalsIgnoreCase("parent")) {
                    intent = new Intent(this, ParentTabActivity.class);
                }

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);

                String channelId = getString(R.string.default_notification_channel_id);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(title)
                                .setContentText(messageBody)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
