package com.example.dosificapp.data;

import com.example.dosificapp.dominio.Usuario;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<Usuario> login(String username, String password) {

        if(username != password){
            return new Result.Error(new IOException("Credenciales incorrectas"));
        }

        String type = "P";

        try {
           switch(username){
               case "paciente": type = "P";
                                break;
               case "acompa": type = "A";
                   break;
               case "ambos": type = "PA";
                   break;
               default: return new Result.Error(new IOException("Credenciales incorrectas"));
           }

           Usuario fakeUser = new Usuario(username, password, type);
           return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}