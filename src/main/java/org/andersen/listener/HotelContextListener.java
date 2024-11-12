package org.andersen.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.andersen.model.Hotel;

@WebListener
public class HotelContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Hotel hotel = new Hotel();

        ServletContext context = sce.getServletContext();
        context.setAttribute("hotel", hotel);
    }
}
