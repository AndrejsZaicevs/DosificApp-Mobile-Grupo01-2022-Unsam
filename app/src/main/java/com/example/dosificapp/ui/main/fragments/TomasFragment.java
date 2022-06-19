package com.example.dosificapp.ui.main.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dosificapp.AlarmReceiver;
import com.example.dosificapp.CalendarTab;
import com.example.dosificapp.MainActivity;
import com.example.dosificapp.R;
import com.example.dosificapp.databinding.FragmentTomasBinding;
import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.dominio.Usuario;
import com.example.dosificapp.dto.LoginDTO;
import com.example.dosificapp.service.BackendService;
import com.example.dosificapp.ui.login.LoginActivity;
import com.example.dosificapp.ui.main.PageViewModelTomas;
import com.example.dosificapp.ui.main.adapters.DosisListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TomasFragment extends AbstractFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModelTomas pageViewModel;
    private FragmentTomasBinding binding;
    private BackendService backendService = new BackendService("", getContext());
    private Usuario usuario;
    ArrayList<Dosis> listDataDosis = new ArrayList<>();


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
        getDoses(listViewDosis);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public String getName() { return "Proximas tomas"; }

    private void getDoses(ListView listViewDosis){
        String url = getString(R.string.baseURL) + "/getDosis";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Aca esta bien
                        //String mockResponse = "[{units:1,dateTime:\"2022-16-18 10:00\",name:\"Medicamento1\"},{units:1,dateTime:\"2022-16-18 13:00\",name:\"Medicamento1\"},{units:1,dateTime:\"2022-16-18 16:00\",name:\"Medicamento1\"},{units:1,dateTime:\"2022-16-18 19:00\",name:\"Medicamento1\"},{units:1,dateTime:\"2022-16-18 22:00\",name:\"Medicamento1\"},{units:1,dateTime:\"2022-16-19 10:00\",name:\"Medicamento1\"},{units:1,dateTime:\"2022-16-19 13:00\",name:\"Medicamento1\"},{units:1,dateTime:\"2022-16-19 16:00\",name:\"Medicamento1\"},{units:1,dateTime:\"2022-16-19 19:00\",name:\"Medicamento1\"},{units:1,dateTime:\"2022-16-19 22:00\",name:\"Medicamento1\"}]";
                        String mockResponse = "[{units:1,dateTime:\"2022-06-18 23:58:00\",name:\"Medicamento1\"}]";
                        Log.d("GOTTEN", mockResponse);
                        listDataDosis.clear();
                        try{
                            JSONArray jsonResponse = new JSONArray(mockResponse);
                            for(int i = 0; i < jsonResponse.length(); i++){
                                JSONObject dosisObjeto = jsonResponse.getJSONObject(i);
                                String test = dosisObjeto.getString("dateTime");
                                Dosis dosis = new Dosis(
                                        1L,
                                        1L,
                                        dosisObjeto.getString("dateTime"),
                                        dosisObjeto.getString("name"),
                                        dosisObjeto.getInt("units")
                                );
                                listDataDosis.add(dosis);
                            }
                            DosisListAdapter adapter = new DosisListAdapter(getContext(), R.layout.listview_toma, listDataDosis);
                            listViewDosis.setAdapter(adapter);
                            setAlarms(listDataDosis);

                        }catch (JSONException e){
                            Toast.makeText(getContext(), "A ocurrido un error al recuperar las tomas", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "bad", Toast.LENGTH_SHORT).show();
            }
        }){

        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void setAlarms(ArrayList<Dosis> listDataDosis){
        for (Dosis dosis: listDataDosis) {

            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
            Intent intent = new Intent(getContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, PendingIntent.FLAG_MUTABLE);

            // Set the alarm to start at 8:30 a.m.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 5);

            alarmManager.set(AlarmManager.RTC_WAKEUP, dosis.getCalendar().getTimeInMillis(), pendingIntent);
        }
    }


}