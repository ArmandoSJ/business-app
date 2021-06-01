package com.developer.jauregui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.*;
import com.developer.jauregui.views.acceso.LogIn;
import com.developer.jauregui.authentication.properties.ConexionBD;
import com.developer.jauregui.authentication.properties.GetConexion;
import com.developer.jauregui.authentication.properties.GetProperties;
import com.developer.jauregui.views.Home;
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
@CssImport("./styles/shared-styles.css")
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
    private final Tabs tabs = new Tabs();
    private final Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();
    private final DrawerToggle dtgMenu = new DrawerToggle();
    private H5 viewTitle;
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

        viewTitle = new H5();
        viewTitle.getStyle().set("text-align","center");


        createMenuLink(Home.class, Home.HOME_VIEW,
                VaadinIcon.HOME.create());
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        setPrimarySection(AppLayout.Section.DRAWER);
        addToDrawer(tabs);
        addToNavbar(dtgMenu);
    }
    private void cargaListener(){

    }

    private void  createMenuLink(Class<? extends Component> target,
                                      String caption, Icon icon) {
        final Tab tab = new Tab();
        final RouterLink routerLink = new RouterLink(null, target);
        routerLink.setClassName("menu-link");
        navigationTargetToTab.put(target, tab);
        routerLink.add(icon);
        routerLink.add(new Span(caption));
        icon.setSize("24px");
        tab.add(routerLink);
        tabs.add(tab);
    }

/*    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
        if (VaadinSession.getCurrent().getAttribute("USUARIOID") == null) {
            VaadinSession.getCurrent().setAttribute("intededPath", event.getLocation().getPath());
            event.forwardTo(LogIn.class);
        }
    }*/



    public static MainView get() {
        return (MainView) UI.getCurrent().getChildren()
                .filter(component -> component.getClass() == MainView.class)
                .findFirst().get();
    }


    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }



    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
}
