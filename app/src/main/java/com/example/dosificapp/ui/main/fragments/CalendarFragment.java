package com.example.dosificapp.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dosificapp.R;
import com.example.dosificapp.databinding.FragmentCalendarTabBinding;
import com.example.dosificapp.dominio.CalendarDay;
import com.example.dosificapp.ui.main.PageViewModel;
import com.example.dosificapp.ui.main.adapters.CalendarListAdapter;

import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class CalendarFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentCalendarTabBinding binding;
    private CalendarDay calendarDay;

    public static CalendarFragment newInstance(CalendarDay calendarDay) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setCalendarDay(calendarDay);
        return fragment;
    }

    public void setCalendarDay(CalendarDay calendarDay) {
        this.calendarDay = calendarDay;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentCalendarTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView calendar = binding.calendarView;
        CalendarListAdapter adapter = new CalendarListAdapter(getContext(), R.layout.listview_calendar, calendarDay.getCalendarItems());
        calendar.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}