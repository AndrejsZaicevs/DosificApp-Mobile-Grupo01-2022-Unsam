package com.example.dosificapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dosificapp.databinding.ActivityCreateAccountBinding;
import com.example.dosificapp.databinding.ActivityLoginBinding;
import com.example.dosificapp.dominio.Usuario;
import com.example.dosificapp.ui.login.LoginActivity;

public class CreateAccount extends AppCompatActivity {

    private Usuario usuario;
    private ActivityCreateAccountBinding binding;

    private Button confirm, cancel;
    private TextView name, lastName, email, number, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        confirm = binding.buttonContinue;
        cancel = binding.buttonCancel;

        name = binding.textNombre;
        lastName = binding.textApellido;
        email = binding.textCorreo;
        number = binding.textNumero;
        id = binding.textDocumento;

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    startActivity(new Intent(CreateAccount.this, Password.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Complete todos los datos antes de continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput() {
        return  name.getText().length() > 0 ||
                lastName.getText().length() > 0 ||
                email.getText().length() > 0 ||
                number.getText().length() > 0 ||
                id.getText().length() > 0;
    }
}
