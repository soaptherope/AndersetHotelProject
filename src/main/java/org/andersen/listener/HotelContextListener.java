package org.andersen.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.andersen.model.Hotel;
import org.andersen.repository.StateRepository;

import java.io.IOException;
import java.sql.SQLException;

@WebListener
public class HotelContextListener implements ServletContextListener {

    private final StateRepository stateRepository = new StateRepository();

    public HotelContextListener() throws SQLException {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Hotel hotel = new Hotel();

        ServletContext context = sce.getServletContext();
        context.setAttribute("hotel", hotel);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Hotel hotel = (Hotel) context.getAttribute("hotel");

        if (hotel != null) {
            try {
                stateRepository.saveSerializedData(hotel);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
