package com.example.dosificapp.ui.login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dosificapp.AlarmReceiver;
import com.example.dosificapp.CreateAccount;
import com.example.dosificapp.MainActivity;
import com.example.dosificapp.R;
import com.example.dosificapp.data.LoginRepository;
import com.example.dosificapp.databinding.ActivityLoginBinding;
import com.example.dosificapp.dominio.Usuario;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LoginRepository loginRepository = LoginRepository.getInstance();
    private ActivityLoginBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button registerButton = binding.register;
        final Button paciente = binding.paciente;
        final Button acompa = binding.acomp;
        final Button ambos = binding.ambos;
        final ProgressBar loadingProgressBar = binding.loading;


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccount.class));
            }
        });

        paciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                login("paciente", "paciente");
            }
        });

        acompa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                login("acompa", "acompa");
            }
        });

        ambos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "La cosa", Toast.LENGTH_SHORT).show();
                loadingProgressBar.setVisibility(View.VISIBLE);
                login("ambos", "ambos");
            }
        });
    }

    private void login(String user, String pass){
        String url = getString(R.string.baseURL) + "/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Aca esta bien
                        Usuario usuario = new Usuario(user,pass,"AP");
                        String imageBase64 = "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";
                        usuario.setImageBase64(imageBase64);
                        loginRepository.setLoggedInUser(usuario);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "bad", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("user",user);
                params.put("pass",pass);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        /*Log.d("CDA", "onBackPressed Called");
        startActivity(new Intent(MainActivity.this, LoginActivity.class));*/
    }
}