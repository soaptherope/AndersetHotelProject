package org.andersen.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.andersen.model.Hotel;
import org.andersen.service.ReservationService;
import org.andersen.service.impl.ApartmentServiceImpl;
import org.andersen.service.impl.ReservationServiceImpl;

import java.io.IOException;

@WebServlet("/reservations")
public class ReservationController extends HttpServlet {

    private ReservationService reservationService;

    @Override
    public void init() {
        ServletContext context = getServletContext();

        Hotel hotel = (Hotel) context.getAttribute("hotel");

        ApartmentServiceImpl apartmentService = new ApartmentServiceImpl(hotel);
        this.reservationService = new ReservationServiceImpl(apartmentService);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        int apartmentId = Integer.parseInt(request.getParameter("id"));
        String clientName = request.getParameter("name");

        if ("reserve".equals(action)) {
            reservationService.reserveApartment(apartmentId, clientName);
        } else if ("release".equals(action)) {
            reservationService.releaseApartment(apartmentId, clientName);
        }

        response.sendRedirect(request.getContextPath() + "/apartments?sortBy=none&pageNumber=1&pageSize=5");
    }
}