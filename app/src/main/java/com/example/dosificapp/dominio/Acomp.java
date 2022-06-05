package com.example.dosificapp.dominio;

public class Acomp {

    private String name;
    private String status;

    public Acomp(String _name, String _status){
        name = _name;
        status = _status;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
