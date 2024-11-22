package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.andersen.controller.ApartmentController;
import org.andersen.model.Apartment;
import org.andersen.service.impl.ApartmentServiceImpl;
import org.andersen.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApartmentControllerTest {

    @InjectMocks
    private ApartmentController apartmentController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HotelServiceImpl hotelService;

    @Mock
    private ApartmentServiceImpl apartmentService;

    @Test
    public void doGet_DefaultSort() throws ServletException, IOException {
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/apartments.jsp")).thenReturn(dispatcher);

        when(request.getParameter("sortBy")).thenReturn(null);
        when(request.getParameter("pageNumber")).thenReturn("1");
        when(request.getParameter("pageSize")).thenReturn("5");
        when(apartmentService.sortByField(1, 5, "none")).thenReturn(Collections.emptyList());

        apartmentController.doGet(request, response);

        verify(apartmentService).sortByField(1, 5, "none");
        verify(dispatcher).forward(request, response);
    }


    @Test
    public void doGet_SortByPrice() throws ServletException, IOException {
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/apartments.jsp")).thenReturn(dispatcher);

        when(request.getParameter("sortBy")).thenReturn("price");
        when(request.getParameter("pageNumber")).thenReturn("1");
        when(request.getParameter("pageSize")).thenReturn("5");
        when(apartmentService.sortByField(1, 5, "price")).thenReturn(Collections.emptyList());

        apartmentController.doGet(request, response);

        verify(apartmentService).sortByField(1, 5, "price");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doGet_SortById() throws ServletException, IOException {
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/apartments.jsp")).thenReturn(dispatcher);

        when(request.getParameter("sortBy")).thenReturn("id");
        when(request.getParameter("pageNumber")).thenReturn("1");
        when(request.getParameter("pageSize")).thenReturn("5");
        when(apartmentService.sortByField(1, 5, "id")).thenReturn(Collections.emptyList());

        apartmentController.doGet(request, response);

        verify(apartmentService).sortByField(1, 5, "id");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doGet_SortByNameOfClient() throws ServletException, IOException {
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/apartments.jsp")).thenReturn(dispatcher);

        when(request.getParameter("sortBy")).thenReturn("nameOfClient");
        when(request.getParameter("pageNumber")).thenReturn("1");
        when(request.getParameter("pageSize")).thenReturn("5");
        when(apartmentService.sortByField(1, 5, "nameOfClient")).thenReturn(Collections.emptyList());

        apartmentController.doGet(request, response);

        verify(apartmentService).sortByField(1, 5, "nameOfClient");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doGet_SortByStatus() throws ServletException, IOException {
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/apartments.jsp")).thenReturn(dispatcher);

        when(request.getParameter("sortBy")).thenReturn("status");
        when(request.getParameter("pageNumber")).thenReturn("1");
        when(request.getParameter("pageSize")).thenReturn("5");
        when(apartmentService.sortByField(1, 5, "status")).thenReturn(Collections.emptyList());

        apartmentController.doGet(request, response);

        verify(apartmentService).sortByField(1, 5, "status");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPost_AddApartment() throws IOException {
        when(request.getParameter("_method")).thenReturn("POST");
        when(request.getParameter("price")).thenReturn("100");

        doNothing().when(hotelService).addApartment(any(Apartment.class));

        apartmentController.doPost(request, response);

        verify(hotelService).addApartment(argThat(apartment -> apartment.getPrice() == 100));
        verify(response).sendRedirect(contains("apartments?sortBy=none&pageNumber=1&pageSize=5"));
    }

    @Test
    public void doDelete_ValidApartmentId() throws IOException {
        long apartmentId = 1L;
        Apartment apartment = new Apartment();
        apartment.setId(apartmentId);

        when(apartmentService.findById(apartmentId)).thenReturn(apartment);
        doNothing().when(apartmentService).delete(apartment);

        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(apartmentId));

        apartmentController.doDelete(request, response);

        verify(apartmentService).findById(apartmentId);
        verify(apartmentService).delete(apartment);
        verify(response).sendRedirect(contains("apartments?sortBy=none&pageNumber=1&pageSize=5"));
    }

    @Test
    public void doDelete_InvalidApartmentId() throws IOException {
        long apartmentId = 999L;

        when(apartmentService.findById(apartmentId)).thenReturn(null);
        when(request.getParameter("apartmentId")).thenReturn(String.valueOf(apartmentId));

        apartmentController.doDelete(request, response);

        verify(apartmentService).findById(apartmentId);
        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
