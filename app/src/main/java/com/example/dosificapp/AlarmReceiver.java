package com.example.dosificapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dosificapp.dominio.Dosis;
import com.example.dosificapp.ui.login.LoginActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //
        Intent newIntent = new Intent(context, AlertActivity.class);
        String idDosis = intent.getAction();
        newIntent.putExtra("dosis", idDosis);
        newIntent.setAction(idDosis);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);
    }
}
