package com.example.dosificapp.ui.login;

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
import com.example.dosificapp.CreateAccount;
import com.example.dosificapp.DosificAppFirebaseMessagingService;
import com.example.dosificapp.MainActivity;
import com.example.dosificapp.R;
import com.example.dosificapp.data.LoginRepository;
import com.example.dosificapp.databinding.ActivityLoginBinding;
import com.example.dosificapp.dominio.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LoginRepository loginRepository = LoginRepository.getInstance();
    private ActivityLoginBinding binding;
    ProgressBar loadingProgressBar;

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
        loadingProgressBar = binding.loading;

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
        String token = DosificAppFirebaseMessagingService.getToken(getApplicationContext());
        String url = getString(R.string.baseURL) + "/api/Base/Login/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Aca esta bien
                        Usuario usuario = new Usuario(user,pass);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            usuario.setId(jsonObject.getInt("Id"));
                            usuario.setNombre(jsonObject.getString("Nombre"));
                            usuario.setApellido(jsonObject.getString("Apellido"));
                            usuario.setNumero(jsonObject.getString("NumeroTel"));
                            usuario.setDocumento(jsonObject.getString("Documento"));
                            usuario.setImageBase64(jsonObject.getString("Foto"));
                            usuario.setTiposUsuario(jsonObject.getJSONArray("TiposUsuarios"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                loadingProgressBar.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("User",user);
                params.put("Password",pass);
                params.put("FcmToken", token);
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