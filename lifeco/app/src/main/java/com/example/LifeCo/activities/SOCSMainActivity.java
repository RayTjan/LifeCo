package com.example.LifeCo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.LifeCo.fragments.SOCSChatFragment;
import com.example.LifeCo.fragments.SOCSNotesFragment;
import com.example.LifeCo.fragments.SOCSProfileFragment;
import com.example.lifeco.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SOCSMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socs_main);

        //toolbar
        toolbar = findViewById(R.id.socs_group_chat_toolbar);

        bottomNavigationView = findViewById(R.id.socs_bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){

                    case R.id.socs_menu_chat:
                        toolbar.setTitle("Group Chat");
                        setSupportActionBar(toolbar);
                        fragment = new SOCSChatFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.socs_menu_notes:
                        toolbar.setTitle("Notes");
                        setSupportActionBar(toolbar);
                        fragment = new SOCSNotesFragment();
                        loadFragment(fragment);
                        return true;

                    case R.id.socs_menu_profile:
                        toolbar.setTitle("Profile");
                        setSupportActionBar(toolbar);
                        fragment = new SOCSProfileFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.socs_frame_main, fragment);
        transaction.commit();
    }
}