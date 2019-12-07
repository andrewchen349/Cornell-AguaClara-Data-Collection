package Util;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.andre.aguaclarapost.About;
import com.andre.aguaclarapost.MainActivity;
import com.andre.aguaclarapost.R;
import com.andre.aguaclarapost.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bottomNavBarHelper {

    public static void enableNav(final Context context, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navigation_about:
                        Intent intent1 = new Intent(context, About.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.navigation_forms:
                        Intent intent2 = new Intent(context, MainActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.navigation_settings:
                        Intent intent3 = new Intent(context, Settings.class);
                        context.startActivity(intent3);
                        break;
                }
                return false;
            }
        });

    }
}
