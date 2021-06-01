package com.developer.jauregui.views.acceso;

import com.developer.jauregui.authentication.inicializar;
import com.developer.jauregui.authentication.properties.ConexionBD;
import com.developer.jauregui.utilerias.Notifications;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.developer.jauregui.authentication.properties.GetConexion;
import com.developer.jauregui.authentication.properties.GetProperties;
import com.developer.jauregui.domain.User;

import java.io.IOException;

@Route(value = "login")
public class LogIn extends VerticalLayout {

    //----clases externas-----//
    private final GetProperties getProperties = new GetProperties();
    private ConexionBD conexionBD;
    private GetConexion con;

    //---Componentes vaadin----//
    private TextField txtUser = new TextField("Username or Email");
    private PasswordField pwdUser = new PasswordField("Password");
    private H3 hTitle = new H3("Log In");
    private VerticalLayout vltConte = new VerticalLayout();
    private final Button btnLogIn = new Button("login");
    private final Button btnSignUp = new Button("sign up");
    private final Button btnRecoverP = new Button("¿Olvidó su usuario o contraseña?");

    private final Notifications ntfError = new Notifications(Notification.Position.MIDDLE,
            NotificationVariant.LUMO_ERROR,3000);
    private final  Binder<User> binder = new Binder<>(User.class);

    public LogIn(){
        setSizeFull();
        //setClassName("log-in");
        cargaConexion();
        inicializar();
        cargaListener();

    }

    private void cargaConexion(){
        try {
            conexionBD = new ConexionBD();
            getProperties.getConexionProperties("conexion", conexionBD,"mysql");
            con.setConexionDB(conexionBD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inicializar(){

        setAlignItems(FlexComponent.Alignment.CENTER);

        btnLogIn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnSignUp.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        btnRecoverP.getElement()
                .setAttribute("theme", "tertiary");
        //btnRecoverP.setWidth("100%");
        /*setAlignItems(FlexComponent.Alignment.CENTER);
        vltConte.setAlignItems(FlexComponent.Alignment.CENTER);
        vltConte.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        txtUser.setWidth("300px");
        pwdUser.setWidth("300px");

        btnLogIn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnLogIn.addClickShortcut(Key.ENTER);
        btnLogIn.setWidth("300px");

        btnSignUp.setWidth("300px");
        btnSignUp.addThemeVariants(ButtonVariant.LUMO_PRIMARY);*/

        initBinder();

        add(buildUI());
    }

    private FormLayout buildUI(){
        FormLayout formLayout = new FormLayout(txtUser, pwdUser, btnRecoverP,  btnLogIn, btnSignUp);

        formLayout.setMaxWidth("400px");
        formLayout.getStyle().set("margin", "0 auto");

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));


        /*formLayout.setColspan(lblTitle, 2);
        formLayout.setColspan(txtDepartamentoID, 2);
        formLayout.setColspan(txaObservacion, 2);*/

        return formLayout;

    }

    private void initBinder(){
        binder.forField(txtUser)
                .asRequired("Obligatory field")
                .bind(User::getvUserID, User::setvUserID);
        binder.forField(pwdUser)
                .asRequired("Obligatory field")
                .bind(User::getvPwd, User::setvPwd);
    }


    private void cargaListener(){
        btnLogIn.addClickListener(this::authentication);
        btnSignUp.addClickListener(event->
                getUI().ifPresent(ui -> ui.navigate(SignUp.class))
        );
    }




    private void authentication(ClickEvent<Button> buttonClickEvent) {
        if(binder.validate().isOk()){
            User usuario;
            try {
                usuario = inicializar.getInstance()
                        .getRecursosDao()
                        .AutentificacionUser(txtUser.getValue().toUpperCase(), pwdUser.getValue());
                if(usuario != null) {
                    if (usuario.getvStatus() == 1) {
                        UI.getCurrent().getSession().setAttribute("USUARIOID", usuario);
                        UI.getCurrent().getSession().setAttribute(User.class, usuario);
                        getUI().ifPresent(ui -> ui.navigate("sistema-de-acceso"));
                    } else {
                        ntfError.setText("Esta cuenta no esta activada porfavor comuniquece con su administrador");
                        ntfError.open();
                    }
                }else{
                    ntfError.setText("Usuario no válido, necesita revisar su información.");
                    ntfError.open();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
