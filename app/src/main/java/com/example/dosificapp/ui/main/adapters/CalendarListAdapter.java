package com.example.dosificapp.ui.main.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dosificapp.R;
import com.example.dosificapp.dominio.Acomp;
import com.example.dosificapp.dominio.CalendarDay;
import com.example.dosificapp.dominio.CalendarItem;

import java.util.ArrayList;

public class CalendarListAdapter extends ArrayAdapter<CalendarItem> {

    private Context mContext;
    int mResource;

    public CalendarListAdapter(@NonNull Context context, int resource, ArrayList<CalendarItem> items) {
        super(context, resource, items);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView hs = convertView.findViewById(R.id.textHs);
        //TextView ampm = convertView.findViewById(R.id.textViewAMPM);

        hs.setText(String.format("%2s",getItem(position).getHsString()).replace(' ', '0')+":00 hs");
        //ampm.setText(getItem(position).getAmPmString());

        CalendarItem calendarItem = getItem(position);
        Log.d("CalendarItem", calendarItem.toString());

        for(int i = 0; i < 3; i++){
            TextView text;
            switch(i){
                case 1 :    text = convertView.findViewById(R.id.textMed2);
                            break;
                case 2 :    text = convertView.findViewById(R.id.textMed3);
                            break;
                default :   text = convertView.findViewById(R.id.textMed1);
                            break;
            }
            try {
                String value = getItem(position).getName(i);
                text.setVisibility(View.VISIBLE);
                text.setText(value);
            }catch(Exception e){

            }
        }

        return convertView;
    }
}
