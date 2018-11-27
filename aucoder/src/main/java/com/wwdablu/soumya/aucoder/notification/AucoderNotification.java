package com.wwdablu.soumya.aucoder.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

public final class AucoderNotification {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    private String createChannel(@NonNull Context context,
                                @NonNull String channelName,
                                @NonNull String channelId) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(notificationManager == null) {
            return null;
        }

        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        return channelId;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    public Notification createNotification(@NonNull Context context,
                                           @NonNull String channelId,
                                           @NonNull String channelName,
                                           @NonNull String title,
                                           @NonNull String message,
                                           @NonNull @DrawableRes int icon) {

        String id = createChannel(context, channelName, channelId);
        if(TextUtils.isEmpty(id)) {
            return null;
        }

        return new Notification.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(icon)
                .setStyle(new Notification.BigTextStyle())
                .build();
    }
}
