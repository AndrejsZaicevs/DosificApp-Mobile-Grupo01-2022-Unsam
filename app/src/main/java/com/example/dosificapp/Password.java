package com.example.dosificapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dosificapp.databinding.ActivityCreateAccountBinding;
import com.example.dosificapp.databinding.ActivityPasswordBinding;
import com.example.dosificapp.dominio.Usuario;

public class Password extends AppCompatActivity {

    private Usuario usuario;
    private ActivityPasswordBinding binding;

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

        password = binding.editTextTextPassword;
        confirmPassword = binding.editTextTextPasswordConfirm;



    }
}