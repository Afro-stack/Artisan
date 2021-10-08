package com.example.artisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.artisan.Fragments.CommunityFragment;
import com.example.artisan.Fragments.HomeFragment;
import com.example.artisan.Fragments.NotificationsFragment;
import com.example.artisan.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StartActivity extends AppCompatActivity {
    Fragment selectorFragment;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        selectorFragment = new HomeFragment();
                       break;
                    case R.id.community:
                        selectorFragment = new CommunityFragment();
                        break;
                    case R.id.post:
                        selectorFragment = null;
                        startActivity(new Intent(StartActivity.this, PostActivity.class));
                        break;
                    case R.id.notifictions:
                        selectorFragment = new NotificationsFragment();
                        break;
                    case R.id.profile:
                        selectorFragment = new ProfileFragment();
                        break;
                }
                if (selectorFragment != null)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
                }
                return true;
            }

        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }
}