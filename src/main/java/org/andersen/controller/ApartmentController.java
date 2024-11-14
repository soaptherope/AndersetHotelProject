package org.andersen.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.andersen.service.ApartmentService;
import org.andersen.service.HotelService;
import org.andersen.service.impl.ApartmentServiceImpl;
import org.andersen.service.impl.HotelServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/apartments")
public class ApartmentController extends HttpServlet {

    private ApartmentService apartmentService;
    private HotelService hotelService;

    @Override
    public void init() {
        ServletContext context = getServletContext();

        Hotel hotel = (Hotel) context.getAttribute("hotel");

        apartmentService = new ApartmentServiceImpl(hotel);
        try {
            this.hotelService = new HotelServiceImpl(hotel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        List<Apartment> apartments;

        if (sortBy == null || "none".equals(sortBy)) {
            apartments = apartmentService.getAllApartments();
        } else {
            apartments = switch (sortBy) {
                case "id" -> apartmentService.sortById(pageNumber, pageSize);
                case "price" -> apartmentService.sortByPrice(pageNumber, pageSize);
                case "nameOfClient" -> apartmentService.sortByNameOfClient(pageNumber, pageSize);
                case "status" -> apartmentService.sortByStatus(pageNumber, pageSize);
                default -> apartmentService.getAllApartments();
            };
        }

        request.setAttribute("apartments", apartments);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/apartments.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        double price = Double.parseDouble(request.getParameter("price"));

        Apartment newApartment = new Apartment(price);
        hotelService.addApartment(newApartment);

        response.sendRedirect(request.getContextPath() + "/apartments?sortBy=none&pageNumber=1&pageSize=5");
    }
}

