package com.example.dosificapp.ui.main.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dosificapp.R;
import com.example.dosificapp.dominio.Acomp;

import java.util.ArrayList;
import java.util.Base64;

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
        String name = getItem(position).getName();
        String status = getItem(position).getStatus();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameTV = convertView.findViewById(R.id.nameAcomp);
        TextView statusTV = convertView.findViewById(R.id.statusAcomp);
        ImageView image = convertView.findViewById(R.id.imageAcompañante);

        byte[] imageBytes = Base64.getDecoder().decode(getItem(position).getBase64image());
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        image.setImageBitmap(decodedImage);

        nameTV.setText(name);
        statusTV.setText(status);

        return convertView;
    }
}
