package com.example.dosificapp;

import android.os.Bundle;

import com.example.dosificapp.data.DosisRepository;
import com.example.dosificapp.dominio.CalendarItem;
import com.example.dosificapp.dominio.Dosis;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dosificapp.ui.main.pageAdapters.CalendarSectionsPagerAdapter;
import com.example.dosificapp.databinding.ActivityCalendarTabBinding;

import java.util.ArrayList;

public class CalendarTab extends AppCompatActivity {

    private ActivityCalendarTabBinding binding;
    private DosisRepository dosisRepository = DosisRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCalendarTabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CalendarSectionsPagerAdapter sectionsPagerAdapter = new CalendarSectionsPagerAdapter(this, getSupportFragmentManager(),dosisRepository.getListaDosis());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}