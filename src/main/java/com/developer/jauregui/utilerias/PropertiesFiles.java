package com.developer.jauregui.utilerias;

import java.io.*;

public class PropertiesFiles {


    public static void createFile(String pFileName) {
        File archivo = new File(pFileName);
        try {
            PrintWriter output = new PrintWriter(archivo);
            output.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void writeFile(String nombreArchivo, String contenido) {
        File archivo = new File(nombreArchivo);
        try {
            PrintWriter salida = new PrintWriter(archivo);
            salida.println(contenido);
            salida.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void fillFile(String pFileName, String pContent) {
        File file = new File(pFileName);
        try {
            PrintWriter salida = new PrintWriter(new FileWriter(file, true));
            salida.println(pContent);
            salida.close();
            System.out.println("Se ha anexado informacion al archivo");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void readFile(String pFileName) {
        File archivo = new File(pFileName);
        try {
            BufferedReader input = new BufferedReader(new FileReader(archivo));
             String lectura = input.readLine();
            while(lectura != null){

                lectura = input.readLine();
            }
            input.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
