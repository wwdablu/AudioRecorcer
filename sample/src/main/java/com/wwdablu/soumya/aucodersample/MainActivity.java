package com.wwdablu.soumya.aucodersample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wwdablu.soumya.aucoder.Aucoder;
import com.wwdablu.soumya.aucoder.AucoderConfig;

public class MainActivity extends AppCompatActivity {

    AucoderConfig config;
    Aucoder aucoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config = Aucoder.Builder()
                .delay(1)
                .channel(AucoderConfig.CHANNEL_STEREO)
                .sampleRate(AucoderConfig.SAMPLE_RATE_44100)
                .recordSource(AucoderConfig.SOURCE_MIC)
                .format(AucoderConfig.FORMAT_3GPP)
                .filename(getFileName())
                .build();

        aucoder = Aucoder.with(MainActivity.this, config);

        findViewById(R.id.btn_start_record).setOnClickListener(view -> aucoder.begin());

        findViewById(R.id.btn_stop_record).setOnClickListener(view -> aucoder.end());
    }

    private String getFileName() {

        String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/aucodertest" + ".3gp";

        return mFileName;
    }
}
