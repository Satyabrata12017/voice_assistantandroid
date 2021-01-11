package com.example.voice_asistant;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.RecognizerIntent;

import androidx.annotation.Nullable;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class classAsistant extends Service {

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
