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
import com.example.dosificapp.dominio.Usuario;

import java.util.Base64;
import java.util.List;

public class UserListAdapter extends ArrayAdapter<Usuario> {

    private Context mContext;
    private int mResource;

    public UserListAdapter(@NonNull Context context, int resource, List<Usuario> users) {
        super(context, resource, users);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getNombre() + " " + getItem(position).getApellido();
        String status = getItem(position).getEmail();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameTV = convertView.findViewById(R.id.nameAcomp);
        TextView statusTV = convertView.findViewById(R.id.statusAcomp);

        if(getItem(position).getImageBase64() != null) {
            ImageView image = convertView.findViewById(R.id.imageAcompañante);

            byte[] imageBytes = Base64.getDecoder().decode(getItem(position).getImageBase64());
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            image.setImageBitmap(decodedImage);
        }

        nameTV.setText(name);
        statusTV.setText(status);

        return convertView;
    }
}
