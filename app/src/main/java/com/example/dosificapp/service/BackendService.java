package com.example.dosificapp.service;

import android.content.Context;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.dominio.Usuario;

import java.util.ArrayList;

public class BackendService {

    String baseUrl;
    Context context;


    public BackendService(String baseUrl, Context context){
        this.baseUrl = baseUrl;
        this.context = context;
    }

    public int createUser(Usuario usuario){
        return 1;
    }

    public boolean login(String user, String pass){
        final boolean[] funcResponse = {false};
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = this.baseUrl + "/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Aca esta bien
                funcResponse[0] = true;
            }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return funcResponse[0];
    }

    public ArrayList<Dosis> getDoses(int idUser){
        ArrayList<Dosis> listDataDosis = new ArrayList<Dosis>();
        return listDataDosis;
    }

    public void sendFCMtoken(String token){

    }


}
