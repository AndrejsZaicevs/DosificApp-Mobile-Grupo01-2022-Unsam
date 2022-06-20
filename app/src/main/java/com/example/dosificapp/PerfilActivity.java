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

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {

    private LoginRepository loginRepository = LoginRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        ImageView image = findViewById(R.id.imageView);

        byte[] imageBytes = Base64.getDecoder().decode(loginRepository.getLoggedInUser().getImageBase64());
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        image.setImageBitmap(Bitmap.createScaledBitmap(decodedImage, 120, 120, false));

        Button salir = findViewById(R.id.buttonSalir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}