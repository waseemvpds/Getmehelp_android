package com.example.getmehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The URL of your live backend
        String url = "https://getmehelp.onrender.com";

        // Save the URL to SharedPreferences for other parts of the app to use
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed = sh.edit();
        ed.putString("url", url);
        ed.apply();

        // Start the login activity and pass the URL directly to it
        Intent i = new Intent(getApplicationContext(), login.class);
        i.putExtra("url", url);
        startActivity(i);

        // Start the background GPS service
        Intent j = new Intent(getApplicationContext(), gpstracker.class);
        startService(j);

        // Finish this activity so the user can't navigate back to the blank screen
        finish();
    }
}