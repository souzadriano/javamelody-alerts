package com.souzadriano.javamelodyalert;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AlertListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Iniciando servlet");
        String application = null;
        try {
            application = sce.getServletContext().getContextPath() + "_" + InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        AlertController alertController = new AlertController(application);
        alertController.initialize();
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
