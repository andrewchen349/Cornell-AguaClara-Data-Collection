package com.andre.aguaclarapost;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import Util.bottomNavBarHelper;

public class About extends AppCompatActivity {

    /* Constants and Fields Go Here */
    public static final int ACTIVITY_NUM = 0;
    private ImageView web;
    private ImageView gith;
    private ImageView fb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setUpBottomNavbar();

        //Find XML Components
        web = (ImageView)findViewById(R.id.globe);
        gith = (ImageView)findViewById(R.id.github);
        fb = (ImageView)findViewById(R.id.facebook);

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://aguaclara.cee.cornell.edu/"));
                startActivity(browserIntent);
            }
        });

        gith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/andrewchen349/AguaClara-POST-ODK"));
                startActivity(browserIntent1);
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent11 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/CUAguaClara/"));
                startActivity(browserIntent11);
            }
        });

    }

    //Bottom Navigation Bar Setup
    private void setUpBottomNavbar() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavbar);
        bottomNavBarHelper.enableNav(About.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
