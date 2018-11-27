package com.wwdablu.soumya.aucoder;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;

final class AucoderMediaRecoderConverter {

    public static int getAudioEncoder(@NonNull AucoderConfig aucoderConfig) {

        switch (aucoderConfig.audioEncoder) {

            case AucoderConfig.AUDIO_ENC_AAC:
                return MediaRecorder.AudioEncoder.AAC;

            case AucoderConfig.AUDIO_ENC_AAC_ELD:
                return MediaRecorder.AudioEncoder.AAC_ELD;

            case AucoderConfig.AUDIO_ENC_AAC_HE:
                return MediaRecorder.AudioEncoder.HE_AAC;

            case AucoderConfig.AUDIO_ENC_AMR_NB:
                return MediaRecorder.AudioEncoder.AMR_NB;

            case AucoderConfig.AUDIO_ENC_AMR_WB:
                return MediaRecorder.AudioEncoder.AMR_WB;

            case AucoderConfig.AUDIO_ENC_VORBSIS:
                return MediaRecorder.AudioEncoder.VORBIS;
        }

        return MediaRecorder.AudioEncoder.AMR_NB;
    }

    @SuppressLint("InlinedApi")
    public static int getOutputFormat(@NonNull AucoderConfig aucoderConfig) {

        switch (aucoderConfig.audioFormat) {

            case AucoderConfig.FORMAT_3GPP:
                return MediaRecorder.OutputFormat.THREE_GPP;

            case AucoderConfig.FORMAT_AAC_ADTS:
                return MediaRecorder.OutputFormat.AAC_ADTS;

            case AucoderConfig.FORMAT_AMR_NB:
                return MediaRecorder.OutputFormat.AMR_NB;

            case AucoderConfig.FORMAT_AMR_WB:
                return MediaRecorder.OutputFormat.AMR_WB;

            case AucoderConfig.FORMAT_MPEG_2_TS:
                return MediaRecorder.OutputFormat.MPEG_2_TS;

            case AucoderConfig.FORMAT_MPEG4:
                return MediaRecorder.OutputFormat.MPEG_4;

            case AucoderConfig.FORMAT_WEBM:
                return MediaRecorder.OutputFormat.WEBM;
        }

        return MediaRecorder.OutputFormat.THREE_GPP;
    }

    public static int getAudioSource(@NonNull AucoderConfig aucoderConfig) {

        switch (aucoderConfig.audioSource) {

            case AucoderConfig.SOURCE_MIC:
                return MediaRecorder.AudioSource.MIC;
        }

        return MediaRecorder.AudioSource.MIC;
    }
}
