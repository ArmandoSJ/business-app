package com.developer.jauregui.authentication.properties;

import com.developer.jauregui.utilerias.Notifications;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class GetProperties extends Properties {

    public GetProperties(){}

    public void getConexionProperties(String pArchivo, HashMap<String, String> pParam, String pTipoCon) throws IOException {
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(pArchivo + ".properties");
            load(inputStream);

            switch (pTipoCon){
                case "mysql":
                    pParam.put("JDBC_URL", getProperty("JDBC_URL", "jdbc:mysql://localhost/escuela?useSSL=false&serverTimezone=UTC"));
                    pParam.put("JDBC_USER", getProperty("JDBC_USER", "root"));
                    pParam.put("JDBC_PASS", getProperty("JDBC_PASS", ""));
                    break;
                case "postgresql":
                    pParam.put("JDBC_URLP", getProperty("JDBC_URLP", "jdbc:postgresql://localhost:5432/bdname"));
                    pParam.put("JDBC_USERP", getProperty("JDBC_USERP", "postgres"));
                    pParam.put("JDBC_PASSP", getProperty("JDBC_PASSP", ""));
                    break;
                 default :
                     pParam.put("JDBC_URLS", getProperty("JDBC_URLS", "jdbc:sqlserver://localhost:1433"));
                     pParam.put("JDBC_USERS", getProperty("JDBC_USERS", "sa"));
                     pParam.put("JDBC_PASSS", getProperty("JDBC_PASSS", ""));
            }

        } catch (Exception e) {
            Notifications ntfException = new Notifications("No se encontró la configuración [ " + pArchivo + " ].. Revisa por favor.",
                    Notification.Position.MIDDLE, NotificationVariant.LUMO_ERROR, 3000);
            ntfException.open();
        }
    }
}
