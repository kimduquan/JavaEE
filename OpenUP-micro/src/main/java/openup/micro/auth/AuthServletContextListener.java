/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.micro.auth;

import java.util.EnumSet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.SessionTrackingMode;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author FOXCONN
 */
@WebListener
public class AuthServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setSessionTrackingModes(EnumSet.of(SessionTrackingMode.URL));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
