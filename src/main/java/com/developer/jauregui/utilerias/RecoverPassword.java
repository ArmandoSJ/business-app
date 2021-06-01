package com.developer.jauregui.utilerias;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.Route;



@Route(value="recover")
public class RecoverPassword extends VerticalLayout  {


    private final H1 header = new H1("Modulo de recuperacion de inicio de sesion");
    private final EmailField emfRecover = new EmailField("Correo electronico");
    private final Button btnRecover = new Button("Recuperar");
    private final Button btnReturn = new Button("Regresar al login",e->getUI().ifPresent(ui -> ui.navigate("login")));
    private final Image imgView2 = new Image("img/butterfly.jpg", "Portada");
    private final VerticalLayout vltView = new VerticalLayout();


    public RecoverPassword() {
        inicializar();
        cargaListener();

    }
    private void inicializar(){
        btnReturn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        imgView2.setWidth("100px");
        imgView2.setHeight("100px");
        emfRecover.setWidth("300px");
        emfRecover.setPlaceholder("Email");
        emfRecover.setPrefixComponent(VaadinIcon.ENVELOPE_O.create());
        setHorizontalComponentAlignment(Alignment.CENTER,imgView2,header,emfRecover,btnRecover,btnReturn);
        vltView.add(imgView2,header,emfRecover,btnRecover,btnReturn);
        add(vltView);


    }
    private void cargaListener(){


    }

    private void recoverPassword(){


    }




}
