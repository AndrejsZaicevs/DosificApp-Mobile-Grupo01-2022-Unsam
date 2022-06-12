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
;
import com.example.dosificapp.MainActivity;
import com.example.dosificapp.R;
import com.example.dosificapp.databinding.FragmentAcompBinding;
import com.example.dosificapp.dominio.Acomp;
import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.ui.login.LoginActivity;
import com.example.dosificapp.ui.main.PageViewModelAcomp;
import com.example.dosificapp.ui.main.adapters.AcompListAdapter;
import com.example.dosificapp.ui.main.adapters.DosisListAdapter;

import java.util.ArrayList;
import java.util.Date;

public class AcompaFragment  extends AbstractFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModelAcomp pageViewModel;
    private FragmentAcompBinding binding;

    public static AcompaFragment newInstance(int index) {
        AcompaFragment fragment = new AcompaFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModelAcomp.class);
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

        binding = FragmentAcompBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Busca las dosis en el local storage
        ListView listAcompa = binding.listAcomp;

        ListView listViewDosis = binding.listAcomp;
        ArrayList<Acomp> listDataAcomp = new ArrayList<Acomp>();
        listDataAcomp.add(new Acomp("Jorge", "Todo bien"));
        listDataAcomp.add(new Acomp("Jorge", "Todo bien"));



        AcompListAdapter adapter = new AcompListAdapter(getContext(), R.layout.listview_acomp, listDataAcomp);
        listViewDosis.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public String getName() {return "Acompañantes";}
}