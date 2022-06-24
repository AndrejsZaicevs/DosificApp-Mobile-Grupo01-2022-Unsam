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
import com.example.dosificapp.dominio.Usuario;
import com.example.dosificapp.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {

    private final LoginRepository loginRepository = LoginRepository.getInstance();
    private final ActivityPerfilBinding binding = ActivityPerfilBinding.inflate(getLayoutInflater());
    private final Usuario user = loginRepository.getLoggedInUser();
    private final ArrayList<Usuario> listaAcomp = new ArrayList<>();

    private final TextView txtNombre = binding.profileName;
    private final TextView txtApellido = binding.profileLastName;
    private final TextView txtEmail = binding.profileEmail;
    private final TextView txtCelular = binding.profilePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        ImageView image = findViewById(R.id.imageView);

        byte[] imageBytes = Base64.getDecoder().decode(loginRepository.getLoggedInUser().getImageBase64());
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        image.setImageBitmap(Bitmap.createScaledBitmap(decodedImage, 120, 120, false));

        txtNombre.setText(user.getNombre());
        txtApellido.setText(user.getApellido());
        txtEmail.setText(user.getEmail());
        txtCelular.setText(user.getNumero());

        Button salir = binding.buttonSalir;
        Button guardar = binding.buttonGuardar;
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

    }
}