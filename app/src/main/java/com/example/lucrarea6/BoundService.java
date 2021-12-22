package com.example.lucrarea6;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.HashSet;

public class BoundService extends Service {
    public BoundService() {
    }

    private final IBinder binder = (IBinder) new LocalBinder();

    public HashSet<String> union(HashSet<String> crowdA, HashSet<String> crowdB) {
        crowdA.addAll(crowdB);

        return crowdA;
    }

    public class LocalBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


}