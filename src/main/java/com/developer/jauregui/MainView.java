package com.developer.jauregui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.*;
import com.developer.jauregui.authentication.properties.ConexionBD;
import com.developer.jauregui.authentication.properties.GetConexion;
import com.developer.jauregui.authentication.properties.GetProperties;
import com.vaadin.flow.theme.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The main view contains a button and a click listener.
 */
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
@Theme(themeFolder = "myapp")
public class MainView extends AppLayout implements PageConfigurator {
        //implements BeforeEnterObserver {


    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");

        settings.addFavIcon("icon", "frontend/images/favicons/favicon.ico",
                "256x256");
    }


    private static final Logger log = LoggerFactory.getLogger(MainView.class);

    private final GetProperties getProperties = new GetProperties();
    private ConexionBD conexionBD;
    private GetConexion con;

    //Componentes
    private final Image imageLogo =  new Image("img/logo.png", "My App logo");
    private final Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();
    private final DrawerToggle dtgMenu = new DrawerToggle();
    private H2 viewTitle;
    private final Locale LocalEs = new Locale("es","MX");

    public MainView() {
        getConnection();
        VaadinSession.getCurrent()
                .setErrorHandler((ErrorHandler) errorEvent -> {
                    log.error("Uncaught UI exception",
                            errorEvent.getThrowable());
                    Notification.show(
                            "Lo sentimos, Pero un error interno ha ocurrido.");
                });
        inicializar();
        cargaListener();

    }

    private void cargaListener() {
    }

    /**
     * Metho
     */
    private void getConnection(){
        try {
            conexionBD = new ConexionBD();
            getProperties.getConexionProperties("conexion", conexionBD,"mysql");
            con.setConexionDB(conexionBD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void inicializar(){
        imageLogo.setHeight("80px");
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setId("header");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        viewTitle = new H2();
        //viewTitle.getStyle().set("text-align","center");
        layout.add(imageLogo, viewTitle);
        layout.add(viewTitle, createMainMenu(), new Avatar());
        return layout;
    }


    private MenuBar createMainMenu(){
        MenuBar menuBar = new MenuBar();

        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
        menuBar.setOpenOnHover(true);

        Text selected = new Text("");

        MenuItem project = menuBar.addItem("Project");
        MenuItem account = menuBar.addItem("Account");
        menuBar.addItem("Sign Out", e -> selected.setText("Sign Out"));

        SubMenu projectSubMenu = project.getSubMenu();
        MenuItem users = projectSubMenu.addItem("Users");
        MenuItem billing = projectSubMenu.addItem("Billing");

        SubMenu usersSubMenu = users.getSubMenu();
        usersSubMenu.addItem("List", e -> selected.setText("List"));
        usersSubMenu.addItem("Add", e -> selected.setText("Add"));

        SubMenu billingSubMenu = billing.getSubMenu();
        billingSubMenu.addItem("Invoices", e -> selected.setText("Invoices"));
        billingSubMenu.addItem("Balance Events",
                e -> selected.setText("Balance Events"));

        account.getSubMenu().addItem("Edit Profile",
                e -> selected.setText("Edit Profile"));
        account.getSubMenu().addItem("Privacy Settings",
                e -> selected.setText("Privacy Settings"));

        return menuBar;
    }


    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }


    public static MainView get() {
        return (MainView) UI.getCurrent().getChildren()
                .filter(component -> component.getClass() == MainView.class)
                .findFirst().get();
    }


    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
}
