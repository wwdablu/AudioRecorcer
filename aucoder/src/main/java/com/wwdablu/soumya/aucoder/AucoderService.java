package com.wwdablu.soumya.aucoder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.wwdablu.soumya.aucoder.notification.AucoderNotification;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public final class AucoderService extends Service {

    public static final String EXTRA_AUCODER_CONFIG = "extra.aucoder.config";

    private final String CHANNEL_ID = "id.recording.persistent.notification";

    private MediaRecorder mediaRecorder;
    private AucoderConfig aucoderConfig;
    private DisposableObserver<Void> recoderObserver;
    private Notification notification;
    private NotificationManager notificationManager;

    private boolean isRecording;


    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new AucoderNotification().createNotification(this,
                    CHANNEL_ID, "Recording Session", "Recording...",
                    "Recording in-progress", R.drawable.ic_recording);
        } else {
            notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setOngoing(true)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.ic_recording)
                .addAction(new NotificationCompat.Action(R.drawable.ic_stop, "Stop", new AucoderNotification().getStopPendingIntent(this)))
                .build();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent == null) {
            return START_NOT_STICKY;
        }

        if(intent.hasExtra("action") && intent.getStringExtra("action").equalsIgnoreCase("stop_recording")) {
            stopAndCompleteOperation();
            return START_NOT_STICKY;
        }

        if(intent.getExtras() == null) {
            return START_NOT_STICKY;
        }

        aucoderConfig = intent.getExtras().getParcelable(EXTRA_AUCODER_CONFIG);

        //Continue with the existing session
        if(recoderObserver != null && !recoderObserver.isDisposed()) {
            return START_NOT_STICKY;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, notification);
        } else {
            notificationManager.notify(1, notification);
        }

        recoderObserver = Observable.create((ObservableOnSubscribe<Void>) emitter -> {

            createMediaRecorder();
            startRecording();
        })
        .delay(aucoderConfig.delay, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<Void>() {
            @Override
            public void onNext(Void aVoid) {
                //
            }

            @Override
            public void onError(Throwable e) {
                stopAndCompleteOperation();
            }

            @Override
            public void onComplete() {
                stopAndCompleteOperation();
            }
        });

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(recoderObserver != null) {
            recoderObserver.onComplete();
        }
    }

    private void stopAndCompleteOperation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
        } else {
            notificationManager.cancel(1);
        }
        stopRecording();
        stopSelf();
    }

    private void ensureNoActiveRecordSession() {

        if(mediaRecorder == null) {
            return;
        }

        stopRecording();
    }

    private void createMediaRecorder() {

        ensureNoActiveRecordSession();

        mediaRecorder = new MediaRecorder();
        try {
            mediaRecorder.setAudioChannels(aucoderConfig.channelMode);
            mediaRecorder.setAudioSource(AucoderMediaRecoderConverter.getAudioSource(aucoderConfig));
            mediaRecorder.setOutputFormat(AucoderMediaRecoderConverter.getOutputFormat(aucoderConfig));
            mediaRecorder.setAudioEncoder(AucoderMediaRecoderConverter.getAudioEncoder(aucoderConfig));
            mediaRecorder.setOutputFile(aucoderConfig.fileName);
            mediaRecorder.prepare();
        } catch (IOException e) {
            stopRecording();
        }
    }

    private void startRecording() {

        if(mediaRecorder == null) {
            return;
        }

        mediaRecorder.start();
        isRecording = true;
    }

    private void stopRecording() {

        if(mediaRecorder == null || !isRecording) {
            return;
        }

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        isRecording = false;
    }
}
