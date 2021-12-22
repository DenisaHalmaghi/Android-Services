package com.example.lucrarea6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    BoundService localService;
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().getCurrentState();

        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);

        startButton.setOnClickListener(v -> startService(v));
        stopButton.setOnClickListener(v -> stopService(v));

        Button connectButton = findViewById(R.id.connectButton);
        Button disconnectButton = findViewById(R.id.disconnectButton);

        connectButton.setOnClickListener(v -> bindService(v));
        disconnectButton.setOnClickListener(v -> unbindService(v));
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
            localService = binder.getService();
            isBound = true;

            Button unionButton = findViewById(R.id.unionButton);
            Button intersectionButton = findViewById(R.id.intersectionButton);
            Button differenceButton = findViewById(R.id.differenceButton);
            Button symetricDifferenceButton = findViewById(R.id.symetricDifferenceButton);

            TextView resultView = findViewById(R.id.resultView);

            EditText a = findViewById(R.id.aEditText);
            EditText b = findViewById(R.id.bEditText);

            HashSet<String> crowdA = stringToHashSet(a.getText().toString());
            HashSet<String> crowdB = stringToHashSet(b.getText().toString());

            unionButton.setOnClickListener(v -> {
                HashSet<String> result = localService.union(crowdA,crowdB);
                resultView.setText(result.toString());
            });

            intersectionButton.setOnClickListener(v -> {
                HashSet<String> result = localService.intersection(crowdA,crowdB);
                resultView.setText(result.toString());
            });

            differenceButton.setOnClickListener(v -> {
                HashSet<String> result = localService.differrence(crowdA,crowdB);
                resultView.setText(result.toString());
            });

            symetricDifferenceButton.setOnClickListener(v -> {
                HashSet<String> result = localService.symetricDifferrence(crowdA,crowdB);
                resultView.setText(result.toString());
            });
        }

        private HashSet<String> stringToHashSet(String text){
            String[] crowd = text.split(",[ ]*");

            HashSet<String> set = new HashSet<>();

            for (String element : crowd) {
                set.add(element);
            }

            return set;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;

            //remove listeners
            findViewById(R.id.unionButton).setOnClickListener(null);
            findViewById(R.id.intersectionButton).setOnClickListener(null);
            findViewById(R.id.differenceButton).setOnClickListener(null);
            findViewById(R.id.symetricDifferenceButton).setOnClickListener(null);
        }
    };

    protected void bindService(View view) {
        Intent intent = new Intent(this, BoundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Toast.makeText(this, "Service Bound", Toast.LENGTH_SHORT).show();
    }

    protected void unbindService(View view) {
        if (isBound) {
            unbindService(connection);
            isBound = false;
            Toast.makeText(this, "Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    }

    // Start the service
    public void startService(View view) {
        Intent intent = new Intent(this, StartService.class);
        intent.putExtra("lifecycle",getLifecycle().getCurrentState().toString());
        startService(intent);
    }
    // Stop the service
    public void stopService(View view) {
        stopService(new Intent(this, StartService.class));
    }
}