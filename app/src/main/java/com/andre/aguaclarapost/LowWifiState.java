package com.andre.aguaclarapost;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import Util.bottomNavBarHelper;

public class LowWifiState extends AppCompatActivity {

    public static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lowwifistate);

        setUpBottomNavbar();

        //Check WIFI Speed
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int linkSpeed = wifiManager.getConnectionInfo().getRssi();
        int level = WifiManager.calculateSignalLevel(linkSpeed, 5);

        if(level >= 2){
            Intent intent = new Intent(LowWifiState.this, MainActivity.class);
            LowWifiState.this.startActivity(intent);

        }
    }

    //Bottom Navigation Bar Setup
    private void setUpBottomNavbar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavbar);
        bottomNavBarHelper.enableNav(LowWifiState.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
