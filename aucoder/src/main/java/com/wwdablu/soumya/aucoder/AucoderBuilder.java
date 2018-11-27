package com.wwdablu.soumya.aucoder;

import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wwdablu.soumya.aucoder.AucoderConfig.Source;
import com.wwdablu.soumya.aucoder.AucoderConfig.SampleRate;
import com.wwdablu.soumya.aucoder.AucoderConfig.ChannelMode;
import com.wwdablu.soumya.aucoder.AucoderConfig.AudioFormat;

public final class AucoderBuilder {

    private AucoderConfig config;

    AucoderBuilder() {
        config = new AucoderConfig();
    }

    public AucoderBuilder delay(int delay) throws IllegalArgumentException {

        if(delay <= -1) {
            throw new IllegalArgumentException("Illegal delay value of " + delay);
        }

        config.delay = delay;
        return this;
    }

    public AucoderBuilder filename(@NonNull String filename) throws IllegalArgumentException {

        if(TextUtils.isEmpty(filename)) {
            throw new IllegalArgumentException("Filename cannot be empty or null");
        }

        config.fileName = filename;
        return this;
    }

    public AucoderBuilder recordSource(@Source int source) {
        config.audioSource = source;
        return this;
    }

    public AucoderBuilder sampleRate(@SampleRate int sampleRate) {
        config.sampleRate = sampleRate;
        return this;
    }

    public AucoderBuilder channel(@ChannelMode int channelMode) {
        config.channelMode = channelMode;
        return this;
    }

    public AucoderBuilder format(@AudioFormat int audioFormat) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O && audioFormat == AucoderConfig.FORMAT_MPEG_2_TS) {
            audioFormat = AucoderConfig.FORMAT_MPEG4;
        }

        config.audioFormat = audioFormat;
        return this;
    }

    @NonNull
    public AucoderConfig build() {
        return config;
    }
}
