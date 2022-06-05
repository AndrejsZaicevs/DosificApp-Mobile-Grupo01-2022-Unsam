package com.example.dosificapp.dominio;

import java.util.Date;

public class Dosis {

    private Long doseId;
    private Long doseTakeid;
    private Date hora;
    private String name;

    public Dosis(Long _doseId, Long _doseTakeid, Date _hora, String _name){
        doseId = _doseId;
        doseTakeid = _doseTakeid;
        hora = _hora;
        name = _name;
    }

    public Long getDoseId() {return doseId; }
    public Long getDoseTakeid() {return doseTakeid; }
    public Date getHora() {return hora; }
    public String getName() {return name;}
}
