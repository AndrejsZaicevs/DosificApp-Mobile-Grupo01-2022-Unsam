package com.example.dosificapp;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class DosificAppFirebaseMessagingService extends FirebaseMessagingService {

    private String TAG = "FirebaseService";

    @Override
    public void onNewToken(@NonNull String token){
        super.onNewToken(token);
        Log.d(TAG,"Token : " + token );
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", token).apply();
    }

    public static String getToken(Context context){
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                Log.d(TAG, "Long job");
            } else {
                // Handle message within 10 seconds
                Log.d(TAG, "Short job");
            }

        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
