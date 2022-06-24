package com.example.dosificapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dosificapp.data.LoginRepository;
import com.example.dosificapp.databinding.ActivityAlertBinding;
import com.example.dosificapp.databinding.ActivityMainBinding;
import com.example.dosificapp.databinding.ActivityPerfilBinding;
import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.dominio.Usuario;
import com.example.dosificapp.ui.login.LoginActivity;
import com.example.dosificapp.ui.main.adapters.DosisListAdapter;
import com.example.dosificapp.ui.main.adapters.UserListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {

    private LoginRepository loginRepository;
    private ActivityPerfilBinding binding;
    private Usuario user;
    private ArrayList<Usuario> listaAcomp = new ArrayList<>();
    private ListView lista;

    private TextView txtNombre;
    private TextView txtApellido;
    private TextView txtEmail;
    private TextView txtCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        loginRepository = LoginRepository.getInstance();
        binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        user = loginRepository.getLoggedInUser();
        listaAcomp = new ArrayList<>();

        txtNombre = findViewById(R.id.profileName);
        txtApellido = findViewById(R.id.profileLastName);
        txtEmail = findViewById(R.id.profileEmail);
        txtCelular = findViewById(R.id.profilePhone);

        lista = findViewById(R.id.listAcomp);

        if(!user.getImageBase64().isEmpty()){
            ImageView image = findViewById(R.id.imageView);
            byte[] imageBytes = Base64.getDecoder().decode(loginRepository.getLoggedInUser().getImageBase64());
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            image.setImageBitmap(Bitmap.createScaledBitmap(decodedImage, 120, 120, false));
        }

        txtNombre.setText(user.getNombre());
        txtApellido.setText(user.getApellido());
        txtEmail.setText(user.getEmail());
        txtCelular.setText(user.getNumero());

        Button salir = findViewById(R.id.buttonSalir);
        Button guardar = findViewById(R.id.buttonGuardar);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getListaAcompañantes();
    }

    private boolean verificarCampos(){
        return (
            txtNombre.getText().toString().length() > 0 &&
            txtApellido.getText().toString().length() > 0 &&
            txtEmail.getText().toString().length() > 0 &&
            txtCelular.getText().toString().length() > 0
        );
    }

    private void enviarUser(){
        if(!verificarCampos()){
            Toast.makeText(getApplicationContext(), "Verifique que no haya campos vacios", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void getListaAcompañantes(){
        String url = getString(R.string.baseURL) + "/api/PacienteAcompaniante/ObtenerDatosPaciente/" + loginRepository.getLoggedInUser().getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listaAcomp.clear();
                        try{
                            JSONObject data = new JSONObject(response);
                            JSONArray acomps = data.getJSONArray("Acompaniantes");
                            for(int i = 0; i < acomps.length(); i++){
                                JSONObject acomp = acomps.getJSONObject(i);
                                Usuario user = new Usuario();
                                user.setNombre(acomp.getString("Nombre"));
                                user.setApellido(acomp.getString("Apellido"));
                                user.setEmail(acomp.getString("Email"));
                                listaAcomp.add(user);
                            }
                            UserListAdapter adapter = new UserListAdapter(getApplicationContext(), R.layout.listview_acomp, listaAcomp);
                            lista.setAdapter(adapter);
                        }catch (JSONException e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){};;
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}