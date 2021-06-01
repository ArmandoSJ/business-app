package com.developer.jauregui.utilerias;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/*
 * Clase para crear una vista de notificacion
 * Creada por asalazarj(developed by asalazarj)
 * Proyecto: Sistema de consulta y pagos en línea.
 * Fecha->16/01/20
 */
public class Notifications extends Notification {

    private Button btnShow;
    private H3 cont;
    private Div content;
    private Paragraph prgCont;

    public Notifications(){
    }

    /**
     * @param position
     *        Posición en la cual se mostrará la notificación
     *        Position in which the notification will be shown
     * @param notificationVariant
     *        Theme de la notificacion
     *        Theme of the notification
     * @param pDuracion
     *        Tiempo de la notificación abierta
     *        Notification time open
     * */
    public Notifications(Position position, NotificationVariant notificationVariant,
                            Integer pDuracion){
        inicializar();
        setPosition(position);
        setDuration(pDuracion);
        addThemeVariants(notificationVariant);

    }
    public void setText(String value){
        this.prgCont.setText(value);
    }
    /**
     * @param pCadena
     *        Texto el cual sera mostrado en la notificacion
     *        Text which will be shown in the notification
     * @param position
     *        Posición en la cual se mostrará la notificación
     *        Position in which the notification will be shown
     * @param notificationVariant
     *        Theme de la notificacion
     *        Theme of the notification
     * @param pDuracion
     *        Tiempo de la notificación abierta
     *        Notification time open
     * */
    public Notifications(String pCadena, Position position, NotificationVariant notificationVariant,
                            Integer pDuracion){
        inicializar();
        this.prgCont.setText(pCadena);
        setPosition(position);
        setDuration(pDuracion);
        addThemeVariants(notificationVariant);

    }
    /**
     * @param Titulo
     *        Header de la notificacion
     *        Notification header
     * @param pCadena
     *        Texto el cual sera mostrado en la notificacion
     *        Text which will be shown in the notification
     * @param position
     *        Posición en la cual se mostrará la notificación
     *        Position in which the notification will be shown
     * @param notificationVariant
     *        Theme de la notificacion
     *        Theme of the notification
     * @param buttonVariant
     *        Theme del botón(color)
     *        Button theme(colour)
     * @param button
     *        botón el cual será mostrado en la notificación
     *        button which will be shown in the notification
     * */
    public Notifications(String Titulo, String pCadena, Position position,
                            NotificationVariant notificationVariant, ButtonVariant buttonVariant, Button button){
        this.btnShow = button;
        inicializarWithDiv();
        this.cont.setText(Titulo);
        this.prgCont.setText(pCadena);
        setPosition(position);
        addThemeVariants(notificationVariant);
        this.btnShow.addThemeVariants(buttonVariant);

    }

    private void inicializar(){
        btnShow = new Button();
        btnShow.getElement().setAttribute("theme", "tertiary");
        btnShow.setIcon(VaadinIcon.CLOSE.create());
        btnShow.addClickListener(buttonClickEvent -> close());

        prgCont = new Paragraph();
        add(new HorizontalLayout(prgCont,btnShow));

    }
    private void inicializarWithDiv(){
        cont = new H3();
        prgCont = new Paragraph();
        content = new Div();

        content.add(cont);
        add(content,prgCont,btnShow);
        isVisible(true);
    }

    public void isVisible(boolean b){
        btnShow.setVisible(b);
    }

    private void cargaListener(){}
}
