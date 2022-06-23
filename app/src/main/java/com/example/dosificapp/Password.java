package com.example.dosificapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dosificapp.databinding.ActivityCreateAccountBinding;
import com.example.dosificapp.databinding.ActivityPasswordBinding;
import com.example.dosificapp.dominio.Usuario;

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
        Toast.makeText(getApplicationContext(), "Mando a crear", Toast.LENGTH_SHORT).show();
    }
}