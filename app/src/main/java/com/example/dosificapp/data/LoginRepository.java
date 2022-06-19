package com.example.dosificapp.data;

import com.example.dosificapp.dominio.Usuario;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private Usuario user = null;

    public LoginRepository() {
        // TODO leer cache de credenciales
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    public Usuario getLoggedInUser(){
        return this.user;
    }

    public void setLoggedInUser(Usuario user) {
        this.user = user;
    }
}