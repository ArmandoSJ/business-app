package com.developer.jauregui.authentication;

import com.developer.jauregui.domain.User;

public interface SentenciasDao {

    User AutentificacionUser(String pUsuario, String pPassword) throws Exception;

    int registrarUser(User user) throws Exception;

    void signOut();
}
