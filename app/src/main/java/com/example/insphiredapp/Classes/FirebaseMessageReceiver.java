package com.example.insphiredapp.Classes;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.insphiredapp.EmployeeActivity.NotificationConfirmationActivity;
import com.example.insphiredapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

public class FirebaseMessageReceiver
        extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();

        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTag(),remoteMessage.getNotification().getColor());
        }


        Log.e("FirebaseMessageReceiver", "  type:    " + remoteMessage.getNotification().getTag()+"   notificationId:   "+remoteMessage.getNotification().getColor());

         /*    final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Heads Up Notification",
                NotificationManager.IMPORTANCE_HIGH
        );

        Intent intent = new Intent(this, NotificationConfirmationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        Notification.Builder notification =
                new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setSmallIcon(R.mipmap.ic_launcher1)
                        .setAutoCancel(true);

        NotificationManagerCompat.from(this).notify(1, notification.build());*/

        super.onMessageReceived(remoteMessage);
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void sendNotification(String messageBody,String type,String notificationId) {
        Log.e("FirebaseMessageReceiver", "Bundle messageBody: " + messageBody);


        PendingIntent pendingIntent;
        Intent intent = new Intent(this, NotificationConfirmationActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("notificationId", notificationId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Intent intent = new Intent("ACTION_GOT_IT");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }


        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();

        String channelId = System.currentTimeMillis() + "";
        //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher));
        notificationBuilder.setContentTitle(getString(R.string.app_name));
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setTimeoutAfter(15000);
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
        //notificationBuilder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+getPackageName()+"/"+R.raw.tring_sms));
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            //Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId, getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                // notificationChannel.setSound(NOTIFICATION_SOUND_URI, audioAttributes);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            int notificationId1 = (int) ((new Date(System.currentTimeMillis()).getTime() / 1000L) % Integer.MAX_VALUE);
            notificationManager.notify(notificationId1, notificationBuilder.build());

        }

    }


}

