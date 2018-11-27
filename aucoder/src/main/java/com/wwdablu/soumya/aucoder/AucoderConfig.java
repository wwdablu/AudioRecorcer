package com.wwdablu.soumya.aucoder;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class AucoderConfig implements Parcelable {

    @IntDef({CHANNEL_MONO, CHANNEL_STEREO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ChannelMode {}

    @IntDef({SAMPLE_RATE_8000, SAMPLE_RATE_11025, SAMPLE_RATE_16000, SAMPLE_RATE_22050,
            SAMPLE_RATE_32000, SAMPLE_RATE_44100, SAMPLE_RATE_48000})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SampleRate {}

    @IntDef({SOURCE_MIC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {}

    @IntDef({FORMAT_3GPP, FORMAT_AAC_ADTS, FORMAT_AMR_NB, FORMAT_AMR_WB, FORMAT_MPEG_2_TS, FORMAT_MPEG4, FORMAT_WEBM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioFormat {}

    @IntDef({AUDIO_ENC_AAC, AUDIO_ENC_AAC_ELD, AUDIO_ENC_AAC_HE, AUDIO_ENC_AMR_NB, AUDIO_ENC_AMR_WB, AUDIO_ENC_VORBSIS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioEncoder {}

    int delay;
    String fileName;

    @ChannelMode  int channelMode;
    @SampleRate   int sampleRate;
    @Source       int audioSource;
    @AudioFormat  int audioFormat;
    @AudioEncoder int audioEncoder;

    AucoderConfig() {
        delay = 0;
        fileName = "recording_" + System.currentTimeMillis();
        channelMode = CHANNEL_STEREO;
        sampleRate = SAMPLE_RATE_44100;
        audioSource = SOURCE_MIC;
        audioFormat = FORMAT_3GPP;
        audioEncoder = AUDIO_ENC_AMR_NB;
    }

    /*
     *
     * PARCELABLE HANDLERS
     *
     */

    public AucoderConfig(Parcel in) {
        delay = in.readInt();
        channelMode = in.readInt();
        sampleRate = in.readInt();
        audioSource = in.readInt();
        audioFormat = in.readInt();
        fileName = in.readString();
        audioEncoder = in.readInt();
    }

    public static final Creator<AucoderConfig> CREATOR = new Creator<AucoderConfig>() {
        @Override
        public AucoderConfig createFromParcel(Parcel in) {
            return new AucoderConfig(in);
        }

        @Override
        public AucoderConfig[] newArray(int size) {
            return new AucoderConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(delay);
        parcel.writeInt(channelMode);
        parcel.writeInt(sampleRate);
        parcel.writeInt(audioSource);
        parcel.writeInt(audioFormat);
        parcel.writeString(fileName);
        parcel.writeInt(audioEncoder);
    }

    /*
     *
     * DEFINITION VALUES
     *
     */

    public static final int CHANNEL_MONO = 1;
    public static final int CHANNEL_STEREO = 2;

    public static final int SAMPLE_RATE_48000 = 48000;
    public static final int SAMPLE_RATE_44100 = 44100;
    public static final int SAMPLE_RATE_32000 = 32000;
    public static final int SAMPLE_RATE_22050 = 22050;
    public static final int SAMPLE_RATE_16000 = 16000;
    public static final int SAMPLE_RATE_11025 = 11025;
    public static final int SAMPLE_RATE_8000  =  8000;

    public static final int SOURCE_MIC = 1;

    public static final int FORMAT_3GPP = 1;
    public static final int FORMAT_AAC_ADTS = 2;
    public static final int FORMAT_AMR_NB = 3;
    public static final int FORMAT_AMR_WB = 4;
    public static final int FORMAT_MPEG_2_TS = 5;
    public static final int FORMAT_MPEG4 = 6;
    public static final int FORMAT_WEBM = 7;

    public static final int AUDIO_ENC_AAC = 1;
    public static final int AUDIO_ENC_AAC_ELD = 2;
    public static final int AUDIO_ENC_AAC_HE = 3;
    public static final int AUDIO_ENC_AMR_NB = 4;
    public static final int AUDIO_ENC_AMR_WB = 5;
    public static final int AUDIO_ENC_VORBSIS = 6;
}
