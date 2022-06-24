package com.example.dosificapp.ui.main.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dosificapp.R;
import com.example.dosificapp.databinding.FragmentAcompBinding;
import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.dominio.Usuario;
import com.example.dosificapp.ui.main.PageViewModelAcomp;
import com.example.dosificapp.ui.main.adapters.UserListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AcompaFragment  extends AbstractFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModelAcomp pageViewModel;
    private FragmentAcompBinding binding;
    ArrayList<Usuario> listDataAcomp = new ArrayList<Usuario>();
    ArrayList<Dosis> listTomasAcomp = new ArrayList<Dosis>();
    ListView listViewDosis;


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
        listViewDosis = binding.listAcomp;

        getAcomp();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getAcomp(){

    }

    private void getTomasAcomp(int idUser){

    }

    @Override
    public String getName() {return "Usuarios acompañados";}
}