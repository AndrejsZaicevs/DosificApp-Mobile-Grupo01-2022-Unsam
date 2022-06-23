package com.example.dosificapp.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

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

    public void setLoggedInUser(Usuario user, Context context) {
        context.getSharedPreferences("login", MODE_PRIVATE).edit().putString("user", user.getUser()).apply();
        context.getSharedPreferences("login", MODE_PRIVATE).edit().putString("pass", user.getPassword()).apply();
        this.user = user;
    }
}