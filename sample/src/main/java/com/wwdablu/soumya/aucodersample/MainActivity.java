package com.wwdablu.soumya.aucodersample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.wwdablu.soumya.aucoder.Aucoder;
import com.wwdablu.soumya.aucoder.AucoderConfig;

public class MainActivity extends AppCompatActivity {

    private AucoderConfig config;
    private Aucoder aucoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config = Aucoder.Builder()
            .delay(0)
            .channel(AucoderConfig.CHANNEL_STEREO)
            .sampleRate(AucoderConfig.SAMPLE_RATE_48000)
            .recordSource(AucoderConfig.SOURCE_MIC)
            .format(AucoderConfig.FORMAT_3GPP)
            .audioEncoder(AucoderConfig.AUDIO_ENC_AMR_NB)
            .filename(getFileName())
            .build();

        aucoder = Aucoder.with(MainActivity.this, config);

        findViewById(R.id.btn_start_record).setOnClickListener(view -> aucoder.begin());
        findViewById(R.id.btn_stop_record).setOnClickListener(view -> aucoder.end());
    }

    private String getFileName() {

        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/aucodertest" + ".3gp";

        return fileName;
    }
}
