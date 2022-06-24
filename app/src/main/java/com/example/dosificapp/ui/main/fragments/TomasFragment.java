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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.dosificapp.data.DosisRepository;
import com.example.dosificapp.data.LoginRepository;
import com.example.dosificapp.databinding.FragmentTomasBinding;
import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.dominio.Usuario;
import com.example.dosificapp.service.BackendService;
import com.example.dosificapp.ui.login.LoginActivity;
import com.example.dosificapp.ui.main.PageViewModelTomas;
import com.example.dosificapp.ui.main.adapters.DosisListAdapter;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TomasFragment extends AbstractFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModelTomas pageViewModel;
    private FragmentTomasBinding binding;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private DosisRepository dosisRepository = DosisRepository.getInstance();
    private LoginRepository loginRepository = LoginRepository.getInstance();

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
        mSwipeRefreshLayout = binding.pullToRefresh;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CalendarTab.class);
                startActivity(intent);
            }
        });

        // Cargo las dosis
        ListView listViewDosis = binding.listCrono;
        getDoses(listViewDosis);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDoses(listViewDosis);
            }
        });


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
        String url = getString(R.string.baseURL) + "/api/PacienteAcompaniante/ObtenerListadoDosis/" + loginRepository.getLoggedInUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Aca esta bien
                        dosisRepository.clearDosis();
                        try{
                            JSONArray jsonResponse = new JSONArray(response);
                            for(int i = 0; i < jsonResponse.length(); i++){
                                // Dosis
                                JSONObject dosisJson = new JSONObject(jsonResponse.getString(i));
                                JSONArray tomas = new JSONArray(dosisJson.getString("Tomas"));
                                for(int j = 0; j < tomas.length(); j++){

                                    JSONObject toma = new JSONObject(tomas.getString(j));
                                    Dosis dosis = new Dosis(
                                            dosisJson.getLong("Id"),
                                            toma.getLong("Id"),
                                            toma.getString("FechaHora"),
                                            dosisJson.getString("Descripcion"),
                                            dosisJson.getInt("Unidades")
                                    );
                                    if(dosis.getCalendar().getTimeInMillis() - System.currentTimeMillis() > 0)
                                    {
                                        dosisRepository.addDosis(dosis);
                                    }
                                }
                            }
                            DosisListAdapter adapter = new DosisListAdapter(getContext(), R.layout.listview_toma, dosisRepository.getDosisVigentes());
                            listViewDosis.setAdapter(adapter);
                            mSwipeRefreshLayout.setRefreshing(false);
                            setAlarms();

                        }catch (JSONException e){
                            Log.d("ERROR RECUPERAR RECETAS", e.toString());
                            Toast.makeText(getContext(), "ERROR RECUPERAR RECETAS", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al recuperar recetas", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");

                Map<String,String> params = new HashMap<String,String>();
                params.put("Inicio","");
                params.put("Fin","");
                return params;
            }
        };;
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void setAlarms(){
        for (Dosis dosis: dosisRepository.getDosisVigentes()) {

            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
            Intent intent = new Intent(getContext(), AlarmReceiver.class);
            intent.setAction(dosis.getDoseTakeid().toString());
            intent.addFlags(0);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, dosis.getCalendar().getTimeInMillis(), pendingIntent);
        }
    }


}