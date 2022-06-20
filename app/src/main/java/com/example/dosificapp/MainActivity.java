package com.example.dosificapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.dosificapp.data.LoginRepository;
import com.example.dosificapp.dominio.Usuario;
import com.example.dosificapp.ui.login.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dosificapp.ui.main.pageAdapters.MainSectionsPagerAdapter;
import com.example.dosificapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LoginRepository userRepo = LoginRepository.getInstance();
    private Usuario user = userRepo.getLoggedInUser();
    private LoginRepository loginRepository = LoginRepository.getInstance();
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainSectionsPagerAdapter sectionsPagerAdapter = new MainSectionsPagerAdapter(this, getSupportFragmentManager(), user);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        toolbar = findViewById(R.id.topAppBar);

        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.salirCuenta) exit();
                if(item.getItemId() == R.id.verPerfil) profile();
                return false;
            }
        });
    }

    private void profile(){
        startActivity(new Intent(MainActivity.this, PerfilActivity.class));
    }

    private void exit(){
        loginRepository.logout();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        /*Log.d("CDA", "onBackPressed Called");
        startActivity(new Intent(MainActivity.this, LoginActivity.class));*/
    }
}