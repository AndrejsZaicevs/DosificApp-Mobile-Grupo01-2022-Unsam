package com.example.dosificapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dosificapp.databinding.ActivityCreateAccountBinding;
import com.example.dosificapp.databinding.ActivityPasswordBinding;
import com.example.dosificapp.dominio.Usuario;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Password extends AppCompatActivity {

    private Usuario usuario;
    private ActivityPasswordBinding binding;
    private TextView user;

    private Button confirm, tyc;
    private EditText password, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usuario = (Usuario) getIntent().getSerializableExtra("user");

        confirm = binding.buttonContinuar;
        tyc = binding.buttontyc;
        user = binding.textUsuario;

        password = binding.editTextTextPassword;
        confirmPassword = binding.editTextTextPasswordConfirm;

        user.setText(usuario.getNombreApellido());

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().length() == 0 || confirmPassword.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "Complete los campos de contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }
                usuario.setPassword(password.getText().toString());
                sendUser();
            }
        });

    }

    private void sendUser(){
        String url = getString(R.string.baseURL) + "/api/PacienteAcompaniante/CrearAcompaniante";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Cuenta creada", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en al creacion de cuenta", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                String horrendo = "{\"Nombre\":\""+usuario.getNombre()+"\",\"Apellido\":\""+usuario.getNombre()+"\",\"Email\":\""+usuario.getEmail()+"\",\"Contrasenia\":\""+usuario.getPassword()+"\",\"NumeroTel\":\""+usuario.getNumero()+"\",\"Documento\":\""+usuario.getDocumento()+"\",\"TipoDocumento\":1,\"IdOrganizacion\":1,\"TipoUsuario\":1}";
                params.put("Acompaniante",horrendo);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}