package com.example.dosificapp.ui.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.dosificapp.CalendarTab;
import com.example.dosificapp.R;
import com.example.dosificapp.databinding.FragmentTomasBinding;
import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.ui.main.PageViewModelTomas;
import com.example.dosificapp.ui.main.adapters.DosisListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TomasFragment extends AbstractFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModelTomas pageViewModel;
    private FragmentTomasBinding binding;
    ArrayList<Dosis> listDataDosis;

    public static TomasFragment newInstance(int index) {
        TomasFragment fragment = new TomasFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModelTomas.class);
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

        binding = FragmentTomasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button button = binding.buttonCrono;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CalendarTab.class);
                intent.putExtra("dosis", listDataDosis);
                startActivity(intent);
            }
        });


        // Cargo las dosis
        ListView listViewDosis = binding.listCrono;
        listDataDosis = new ArrayList<Dosis>();
        listDataDosis.add(new Dosis(1L, 1L, "2022-06-13 16:02:37", "test1"));
        listDataDosis.add(new Dosis(1L, 1L, "2022-06-13 16:02:37", "Otro medicamento"));
        listDataDosis.add(new Dosis(2L, 2L, "2022-06-14 16:02:37", "test2"));
        listDataDosis.add(new Dosis(3L, 3L, "2022-06-13 01:02:37", "test3"));
        listDataDosis.add(new Dosis(4L, 4L, "2022-06-20 16:02:37", "test4"));
        listDataDosis.add(new Dosis(5L, 5L, "2022-06-20 16:02:37", "test5"));
        listDataDosis.add(new Dosis(6L, 6L, "2022-06-20 16:02:37", "test6"));

        DosisListAdapter adapter = new DosisListAdapter(getContext(), R.layout.listview_toma, listDataDosis);
        listViewDosis.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public String getName() { return "Proximas tomas"; }
}