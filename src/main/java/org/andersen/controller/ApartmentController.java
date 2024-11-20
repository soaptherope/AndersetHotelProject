package org.andersen.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.andersen.model.Apartment;
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
        apartmentService = new ApartmentServiceImpl();
        try {
            this.hotelService = new HotelServiceImpl();
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
            apartments = apartmentService.findAllApartments();
        } else {
            apartments = switch (sortBy) {
                case "id" -> apartmentService.sortById(pageNumber, pageSize);
                case "price" -> apartmentService.sortByPrice(pageNumber, pageSize);
                case "nameOfClient" -> apartmentService.sortByNameOfClient(pageNumber, pageSize);
                case "status" -> apartmentService.sortByStatus(pageNumber, pageSize);
                default -> apartmentService.findAllApartments();
            };
        }

        request.setAttribute("apartments", apartments);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/apartments.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String method = request.getParameter("_method");

        if ("DELETE".equalsIgnoreCase(method)) {
            doDelete(request, response);
        } else {
            double price = Double.parseDouble(request.getParameter("price"));
            Apartment newApartment = new Apartment(price);
            hotelService.addApartment(newApartment);

            response.sendRedirect(request.getContextPath() + "/apartments?sortBy=none&pageNumber=1&pageSize=5");
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long apartmentId = Long.parseLong(request.getParameter("apartmentId"));

        Apartment apartment = apartmentService.findById(apartmentId);
        if (apartment == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        apartmentService.deleteApartment(apartment);

        response.sendRedirect(request.getContextPath() + "/apartments?sortBy=none&pageNumber=1&pageSize=5");
    }

}

