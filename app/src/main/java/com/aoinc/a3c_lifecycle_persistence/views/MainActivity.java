package com.aoinc.a3c_lifecycle_persistence.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aoinc.a3c_lifecycle_persistence.R;
import com.aoinc.a3c_lifecycle_persistence.util.Constants;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private int count = 0;

    private TextView helloText;
    private Button button1;
    public static final String TAG = "TAG_X";

    private SharedPreferences sharedPreferences;

    private SharedPreferences sharedPreferencesEncrypted;
    private String alias = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Activity1: onCreate()");

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

                sharedPreferencesEncrypted = EncryptedSharedPreferences.create(
                        getPackageName(),
                        alias,
                        this,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            }
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

//        count = sharedPreferences.getInt(Constants.COUNT_KEY, 0);
        count = sharedPreferencesEncrypted.getInt(Constants.COUNT_KEY, 0);

        helloText = findViewById(R.id.hello_text);
        udpateText();

        helloText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity2.class));
            }
        });

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                udpateText();
            }
        });
    }

    private void udpateText() {
        helloText.setText("Count: " + count);
//        sharedPreferences.edit().putInt(Constants.COUNT_KEY, count).apply();
        sharedPreferencesEncrypted.edit().putInt(Constants.COUNT_KEY, count).apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity1: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Activity1: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Activity1: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Activity1: onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Activity1: onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity1: onDestroy()");
    }
}