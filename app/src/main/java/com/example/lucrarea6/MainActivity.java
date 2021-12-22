package com.example.lucrarea6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().getCurrentState();

        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);

        startButton.setOnClickListener(v -> startService(v));

        stopButton.setOnClickListener(v -> stopService(v));
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