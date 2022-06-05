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
import com.example.dosificapp.dominio.Acomp;

import java.util.ArrayList;
import java.util.Date;

public class AcompListAdapter extends ArrayAdapter<Acomp> {

    private Context mContext;
    private int mResource;

    public AcompListAdapter(@NonNull Context context, int resource, ArrayList<Acomp> acomps) {
        super(context, resource, acomps);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //String name = getItem(position).getName();
        //String status = getItem(position).getStatus();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameTV = convertView.findViewById(R.id.nameAcomp);
        TextView statusTV = convertView.findViewById(R.id.statusAcomp);

        nameTV.setText("name");
        statusTV.setText("status");

        return convertView;
    }
}
