package com.developer.jauregui.views.acceso;

import com.developer.jauregui.authentication.inicializar;
import com.developer.jauregui.utilerias.Notifications;
import com.developer.jauregui.utilerias.Validation;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.developer.jauregui.domain.User;

import java.security.MessageDigest;

@Route(value = "signup")
public class SignUp extends VerticalLayout {

    private Binder<User> binder = new Binder<>(User.class);
    private Validation vt = new Validation();

    //----componentes---//
    private TextField txtUser = new TextField("User");
    private EmailField emfEmail = new EmailField("Email");
    private PasswordField pwdUser = new PasswordField("Password");
    private PasswordField pwdVer = new PasswordField("Password match");
    private H3 hTitle = new H3("Sign UP");

    private VerticalLayout vltCont = new VerticalLayout();
    private Button btnCreate = new Button("Create User");
    private Button btnRegresar = new Button("Back to login", event-> getUI().ifPresent(ui -> ui.navigate("login")));
    public SignUp(){
        setSizeFull();
        setClassName("sign-up");
        inicializar();
        cargaListener();
    }
    private void inicializar(){

        binder.forField(txtUser)
                .asRequired("Obligatory field")
                .bind(User::getvEmail,User::setvEmail);

        binder.forField(emfEmail)
                .asRequired("Obligatory field")
                .withValidator(new EmailValidator(
                        "invalid email format"))
                .bind(User::getvEmail,User::setvEmail);

        binder.forField(pwdUser)
                /*
                 * @param pwdUser
                 * The method return 2 different result
                 * 1->Password(Valid Password)
                 * 2->Password(Invalid Password)
                 * */
                .asRequired("Obligatory field")
                .withValidator(
                        pswPwd -> pswPwd.length() >= 6,
                        "password must be at least 8 characters")
                .withValidator(pswPwd-> vt.validaPas(pswPwd) == 1,"Contraseña Invalida, La contraseña debe contener al menos una mayúscula y al menos un número")
                .bind(User::getvPwd, User::setvPwd);

        binder.forField(pwdVer)
                /*
                 * @param pwdUser
                 * @param pswVer
                 * The method return 2 different result
                 * true->(password matches)
                 * false->(Password does not match)
                 **/
                .withValidator(pswVer -> vt.areEqualThesePassword(pwdUser.getValue(),pswVer) == true,
                        "password do not match")
                .bind(User::getvPwd, User::setvPwd);


        setAlignItems(FlexComponent.Alignment.CENTER);
        vltCont.setAlignItems(FlexComponent.Alignment.CENTER);
        vltCont.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        txtUser.setWidth("300px");
        emfEmail.setWidth("300px");

        pwdUser.setWidth("300px");
        pwdVer.setWidth("300px");


        btnCreate.setWidth("300px");
        btnCreate.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        btnRegresar.setWidth("300px");
        btnRegresar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);



        vltCont.setSizeFull();
        vltCont.getStyle().set("overflow","auto");
        vltCont.add(hTitle,txtUser,emfEmail,pwdUser,pwdVer,btnCreate,btnRegresar);
        add(vltCont);

    }
    private void cargaListener(){
        btnCreate.addClickListener(this::createUser);
    }

    private void createUser(ClickEvent<Button> event) {
        if(binder.validate().isOk()){
            System.out.println("it has clicked the btn login");
            User user = new User(txtUser.getValue(),1,emfEmail.getValue(),pwdUser.getValue(),1,unique(txtUser.getValue().trim().toLowerCase()+emfEmail.getValue().trim().toLowerCase()));
            addUser(user);
        }
    }

    private final Notifications ntfSuccess = new Notifications(Notification.Position.TOP_START,
            NotificationVariant.LUMO_SUCCESS, 5000);
    private final Notifications ntfException = new Notifications(Notification.Position.TOP_START,
            NotificationVariant.LUMO_ERROR, 4000);

    private void addUser(User user){
        try{
            int vInsert = inicializar.getInstance().getRecursosDao().registrarUser(user);
            if (vInsert == 1) { //if the status response is 200 show a success message and directs to LogIn
                ntfSuccess.setText("El usuario se ha registrado correctamente");//message which will be shown in the notification
                ntfSuccess.open();
                this.getUI().ifPresent(ui -> ui.navigate("login"));
            } else if (vInsert == 0) { //if the status response is 500 show a error message.
                ntfException.setText("El usuario no se ha podido registrar este usuario, intente con nuevos datos");
                ntfException.open();
            }
        }catch (Exception e){
            ntfException.setText("Ha ocurrido una exception al momento de registrarse"+ e.getMessage());
            ntfException.open();
        }
    }


    //method to generate a unique key using SHA-256
    private  static String unique(String text){
        String vResult ="";
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i=0;i<hash.length;i++){
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() ==1) hexString.append('0');
                vResult = hexString.append(hex).toString();
            }

        }catch (Exception e){}
        return vResult;
    }
}
