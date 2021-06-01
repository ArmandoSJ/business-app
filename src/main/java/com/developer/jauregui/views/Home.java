package com.developer.jauregui.views;

import com.developer.jauregui.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "sistema-de-acceso", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
public class Home extends VerticalLayout {

    public static final String HOME_VIEW = "Home";

    private final H1 vTitle = new H1("");

    public Home(){
        inicializar();
        cargaListener();
    }
    private void inicializar(){
        add(vTitle);
    }
    private void cargaListener(){}

}
