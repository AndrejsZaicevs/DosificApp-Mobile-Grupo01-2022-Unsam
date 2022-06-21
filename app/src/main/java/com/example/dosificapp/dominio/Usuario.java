package com.example.dosificapp.dominio;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String numero;
    private String documento;
    private String type;
    private ArrayList<String> tiposUsuario = new ArrayList<>();
    private String password;
    private String user;
    private String imageBase64;

    public Usuario(String user, String password) {
        this.type = type;
        this.password = password;
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        String returnType = "";
        if(tiposUsuario.contains("ACOMPANIANTE")) {
            returnType = returnType + "A";
        }
        if(tiposUsuario.contains("PACIENTE")) {
            returnType = returnType + "P";
        }
        return type;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDisplayName(){
        return this.user;
    }

    public ArrayList<String> getTiposUsuario() {
        return tiposUsuario;
    }

    public void setTiposUsuario(JSONArray tiposUsuario) {
        for(int i = 0; i < tiposUsuario.length(); i++){
            try {
                tiposUsuario.put(tiposUsuario.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
