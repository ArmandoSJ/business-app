package com.developer.jauregui.utilerias;

import com.vaadin.flow.component.notification.Notification;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*Clase para validar Correo electronico y Contraseña
*sistema-de-acceso
*Fecha->5/21/2020
* */


public class Validation {
    /*Metodo para Validar Contraseña
    * Contaseña valida
    * 1 Mayuscula,Letras y numeros
    * Contraseña invalidas
    * Puras Letras,Puros numeros o ingresar una contraseña sin una mayucula
    * */

    public int validaPas(String password){
        int contador = 0;
        try {

            /*
             * Convierte todos los caracteres de la cadena en letras minúsculas y mayusculas y se comparan con la contraseña.
             * Proceso para validar que la contraseña no contenga solo letras o numeros
             *
             * */
            boolean hasUppercase = !password.equals(password.toLowerCase());
            boolean hasLowercase = !password.equals(password.toUpperCase());

            if (hasUppercase)//Si es true contador aumenta 1
                contador++;
            if (hasLowercase)//Si es true contador aumenta 1
                contador++;
            //Valida que contenga letras y numeros de minumo 6 a 15 caracteres y minimo una mayuscula
            Pattern p1 = Pattern.compile("^[a-z0-9]{6,15}[A-Z]{1,}$", Pattern.CASE_INSENSITIVE);
            Matcher m = p1.matcher(password);
            if (m.find())// si encuentra que la contraseña cumple con lo valido
                contador++; //el contrador aumenta 1 y si no contador se queda en su contenido anterior
            //Valida si tiene uno o mas caracteres repetidos.(\ d es una expresión regular para que coincida con un dígito, + coincide con el token anterior (un dígito) una o más veces.)
            Pattern p2 = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
            Matcher m2 = p2.matcher(password);
            if (m2.find())
                contador++;


        }catch (Exception e){
            Notification.show("Error al Verificar la password: "+e.getMessage());
        }
        if (contador >= 3)
            return 1;
        else
            return 2;//"Invalid Password"

    }
    /*Metodo para Validar Correo electronico*/
    public boolean isValidEmailAddress(String email) {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            Pattern p = Pattern.compile(ePattern);
            Matcher m = p.matcher(email);
            return m.matches();
    }

    public boolean areEqualThesePassword(String psw1,String psw2){
        if(psw1.equals(psw2))
            return true;
                else {return false;}

    }
    //Metodo para validar la cantidad a a pagar en pagos en linea
    public boolean lessThanOrEqualTo(BigDecimal txtPago, BigDecimal Totales){
        boolean vFlag = false;
        /*System.out.println(txtPago);
        System.out.println("big decimal total"+Totales.setScale(2, RoundingMode.HALF_EVEN));
        System.out.println("El pago introducido es mayor a la cantidad a pagar "+(txtPago.compareTo(Totales.setScale(2, RoundingMode.HALF_EVEN)) > 0));
        System.out.println("El pago introducido es menor a la cantidad a pagar "+(txtPago.compareTo(Totales.setScale(2, RoundingMode.HALF_EVEN)) < 0));
        System.out.println("El pago introducido es igual a la cantidad a pagar "+(txtPago.compareTo(new BigDecimal("0.01")) < 0));*/
        if(txtPago.compareTo(Totales.setScale(2, RoundingMode.HALF_EVEN)) > 0 || txtPago.compareTo(new BigDecimal("0.01")) < 0 ){
            vFlag = true;
        }else if(txtPago.compareTo(Totales.setScale(2, RoundingMode.HALF_EVEN)) < 0) {
            vFlag = false;
        }
        return vFlag;
    }
}
