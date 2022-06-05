package com.example.dosificapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.dosificapp.data.LoginRepository;
import com.example.dosificapp.data.model.LoggedInUser;
import com.example.dosificapp.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dosificapp.ui.main.SectionsPagerAdapter;
import com.example.dosificapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LoginRepository userRepo = LoginRepository.getInstance();
    private LoggedInUser user = userRepo.getLoggedInUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}