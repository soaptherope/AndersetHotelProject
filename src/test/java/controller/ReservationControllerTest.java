package controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.andersen.controller.ReservationController;
import org.andersen.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ReservationService reservationService;

    @Mock
    private ServletContext servletContext;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    public void setUp() {
        lenient().when(request.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void doPost_ReserveApartment() throws Exception {
        when(request.getParameter("action")).thenReturn("reserve");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Alisher");

        reservationController.doPost(request, response);

        verify(reservationService).reserveApartment(1, "Alisher");

        verify(response).sendRedirect("null/apartments?sortBy=none&pageNumber=1&pageSize=5");
    }

    @Test
    public void doPost_ReleaseApartment() throws Exception {
        // Given: mock request parameters
        when(request.getParameter("action")).thenReturn("release");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("Alisher");

        reservationController.doPost(request, response);

        verify(reservationService).releaseApartment(1, "Alisher");

        verify(response).sendRedirect("null/apartments?sortBy=none&pageNumber=1&pageSize=5");
    }
}
