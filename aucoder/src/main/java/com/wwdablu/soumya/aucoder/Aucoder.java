package com.wwdablu.soumya.aucoder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

public class Aucoder {

    private Context context;
    private AucoderConfig aucoderConfig;

    public static AucoderBuilder Builder() {
        return new AucoderBuilder();
    }

    public static Aucoder with(@NonNull Context context,
                               @NonNull AucoderConfig aucoderConfig) {

        return new Aucoder(
                context,
                aucoderConfig);
    }

    public void begin() {

        Intent intent = new Intent(context, AucoderService.class);
        intent.putExtra(AucoderService.EXTRA_AUCODER_CONFIG, aucoderConfig);
        ContextCompat.startForegroundService(context, intent);
    }

    public void end() {
        Intent intent = new Intent(context, AucoderService.class);
        context.stopService(intent);
    }

    /*
     *
     * PRIVATE METHODS
     *
     */

    private Aucoder(Context context,
                    AucoderConfig aucoderConfig) {

        this.context = context;
        this.aucoderConfig = aucoderConfig;
    }
}
