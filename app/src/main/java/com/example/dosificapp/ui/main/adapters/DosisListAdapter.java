package com.example.dosificapp.ui.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dosificapp.R;
import com.example.dosificapp.dominio.Dosis;

import java.util.ArrayList;
import java.util.Date;

public class DosisListAdapter extends ArrayAdapter<Dosis> {

    private Context mContext;
    int mResource;

    public DosisListAdapter(@NonNull Context context, int resource, ArrayList<Dosis> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String hora = getItem(position).getHora();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameTV = convertView.findViewById(R.id.nameDosis);
        TextView horaTV = convertView.findViewById(R.id.horaDosis);

        nameTV.setText(name);
        horaTV.setText(hora);

        return convertView;
    }
}
