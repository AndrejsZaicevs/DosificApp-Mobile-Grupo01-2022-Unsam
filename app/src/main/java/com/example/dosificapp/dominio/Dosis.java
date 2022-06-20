package com.example.dosificapp.dominio;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Dosis implements Serializable {

    private Long doseId;
    private Long doseTakeid;
    private Calendar hora;
    private String name;
    private int units;
    private String state;
    SimpleDateFormat sdf;

    public Dosis(Long _doseId, Long _doseTakeid, String _hora, String _name, int units){
        doseId = _doseId;
        doseTakeid = _doseTakeid;
        name = _name;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        hora = Calendar.getInstance();
        try {
            hora.setTime(sdf.parse(_hora));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Long getDoseId() {return doseId; }
    public Long getDoseTakeid() {return doseTakeid; }
    public String getHora() {
        return sdf.format(hora.getTime());
    }
    public Calendar getCalendar(){ return hora; }
    public String getName() {return name;}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
