package com.example.lucrarea6;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class StartService extends Service {
    public StartService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
    }
    @Override
    public int onStartCommand(@NonNull Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started -" + intent.getStringExtra("lifecycle") , Toast.LENGTH_LONG).show();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }
}