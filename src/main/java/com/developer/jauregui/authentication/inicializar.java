package com.developer.jauregui.authentication;




public class inicializar {
    private static final inicializar instaciar = new inicializar();
    private final SentenciasDao sentenciasDao = new SentenciasUserDao();

    private inicializar(){
    }

    public static inicializar getInstance() {
        return instaciar;
    }

    public SentenciasDao getRecursosDao() {
        return sentenciasDao;
    }
}
