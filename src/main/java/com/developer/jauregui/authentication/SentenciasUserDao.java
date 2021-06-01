package com.developer.jauregui.authentication;

import com.developer.jauregui.authentication.properties.GetConexion;
import com.developer.jauregui.domain.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class SentenciasUserDao extends GetConexion implements SentenciasDao {



    public SentenciasUserDao() {
    }

    public User AutentificacionUser (String pUsuario, String pPassword) throws Exception{

        User usuario = null;
        PreparedStatement stmt = null;
        try(Connection connection = getConexionDB()){

            String query ="select UserID, RoleID, EmailUser, PwdUser, Status from users where UserID = ? and PwdUser = ? ";

            stmt = connection.prepareStatement(query);
            stmt.setString(1,pUsuario);
            stmt.setString(2,pPassword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String vUserID = rs.getString("UserID");
                Integer vRole = rs.getInt("RoleID");
                String vEmail = rs.getString("EmailUser");
                String vPwd = rs.getString("PwdUser");
                Integer vStatus = rs.getInt("Status");

               usuario = new User (vUserID,vRole,vEmail,vPwd,vStatus);
            }
        }

        return usuario;
    }

    public int registrarUser(User user) throws SQLException {

        int rows = 0;
        PreparedStatement stmt = null;
        try(Connection connection = getConexionDB()){

        String query ="Insert into Users(UserID, RoleID, EmailUser, PwdUser, Status, Token) values (?, ?, ?, ?, ?, ?)";
        stmt = connection.prepareStatement(query);
        stmt.setString(1,user.getvUserID());
        stmt.setInt(2,user.getvRole());
        stmt.setString(3,user.getvEmail());
        stmt.setString(4,user.getvPwd());
        stmt.setInt(5,user.getvStatus());
        stmt.setString(6,user.getvToken());

        rows = stmt.executeUpdate();
        }

        return rows;
    }


    public void signOut() {
        VaadinSession.getCurrent().getSession().invalidate();
        UI.getCurrent().getSession().close();
        UI.getCurrent().navigate("Login");
    }

}
