package com.example.getmehelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class home2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String url; // To hold the url for logout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        // Get URL from SharedPreferences to pass it during logout
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            // Set a default view when the activity is first created
            // You can replace this with a default fragment if you have one
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {
            startActivity(new Intent(getApplicationContext(), view_profile.class));
        } else if (id == R.id.Fuel_Provider) {
            startActivity(new Intent(getApplicationContext(), view_nearby_Fuelprovider.class));
        } else if (id == R.id.mechanic) {
            startActivity(new Intent(getApplicationContext(), View_Nearby_Mechanic.class));
        } else if (id == R.id.Fuel_Provider_Request_Status) {
            startActivity(new Intent(getApplicationContext(), view_fuel_booking_status.class));
        } else if (id == R.id.Mechanic_Request_Status) {
            startActivity(new Intent(getApplicationContext(), View_Mechanic_Booking_status.class));
        } else if (id == R.id.Payment_History) {
            startActivity(new Intent(getApplicationContext(), View_Payment_History.class));
        } else if (id == R.id.feedback) {
            startActivity(new Intent(getApplicationContext(), feedback.class));
        } else if (id == R.id.reply) {
            startActivity(new Intent(getApplicationContext(), View_Reply.class));
        } else if (id == R.id.logout) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            Intent i = new Intent(getApplicationContext(), login.class);
            i.putExtra("url", url);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home2, menu);
        return true;
    }
}