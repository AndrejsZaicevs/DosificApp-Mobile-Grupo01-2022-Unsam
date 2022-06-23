package com.example.dosificapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dosificapp.data.DosisRepository;
import com.example.dosificapp.databinding.ActivityAlertBinding;
import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.dominio.Usuario;
import com.example.dosificapp.ui.login.LoginActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AlertActivity extends AppCompatActivity {

    private DosisRepository dosisRepository = DosisRepository.getInstance();
    private ActivityAlertBinding binding;
    private Dosis dosis;
    private int dosisId;
    Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        binding = ActivityAlertBinding.inflate(getLayoutInflater());

        dosisId = getIntent().getExtras().getInt("dosis");

        dosis = dosisRepository.getDosisTomaById(dosisId);

        TextView textDosis = (TextView) findViewById(R.id.medicamento);
        textDosis.setText(dosis.getName());

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button post = findViewById(R.id.post);
        Button tomar =  findViewById(R.id.tomar);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nofify("PostergarNotificacionDosis");
                Toast.makeText(getApplicationContext(), "Toma pospuesta por "+dosis.getIntervaloPost()+" minutos", Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, dosis.getIntervaloPost());

                //if(calendar.getTimeInMillis() + System.currentTimeMillis() < 0){

                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    Gson gson = new Gson();
                    intent.setAction(String.valueOf(dosisId));
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                //}

                r.stop();
                finish();
            }
        });

        tomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nofify("ConfirmarTomaDosis");
                Toast.makeText(getApplicationContext(), "Toma Confirmada", Toast.LENGTH_SHORT).show();
                r.stop();
                finish();
            }
        });

        nofify("ActualizarNotificacionDosis");
    }

    private void nofify(String path){
        /*
        * api/PacienteAcompaniante/ConfirmarTomaDosis/{idDosisToma}
        * api/PacienteAcompaniante/PostergarNotificacionDosis/{idDosisToma}
        * api/PacienteAcompaniante/ActualizarNotificacionDosis/{idDosisToma}
        * */
        String url = getString(R.string.baseURL) + "api/PacienteAcompaniante/"+path+"/" + dosisId;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Resṕonse", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Resṕonse", error.toString());
            }
        }){};
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}