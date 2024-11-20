<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.andersen.model.Apartment" %>
<%@ page import="org.andersen.model.ApartmentStatusEnum" %>

<html>
<head>
    <title>Apartment List</title>
</head>
<body>
    <h1>Apartment List</h1>

    <!-- Sorting Form -->
    <form action="apartments" method="GET">
        <label for="sortBy">Sort By:</label>
        <select name="sortBy" id="sortBy">
            <option value="id">ID</option>
            <option value="price">Price</option>
            <option value="nameOfClient">Name of Client</option>
            <option value="status">Status</option>
        </select>
        <input type="number" name="pageNumber" value="<%= request.getParameter("pageNumber") != null ? request.getParameter("pageNumber") : 1 %>" hidden>
        <input type="number" name="pageSize" value="5" hidden>
        <button type="submit">Sort</button>
    </form>

    <hr>

    <!-- Add Apartment Form -->
    <h2>Add New Apartment</h2>
    <form action="apartments" method="POST">
        <label for="price">Price:</label>
        <input type="number" name="price" id="price" required><br>
        <button type="submit">Add Apartment</button>
    </form>

    <hr>

    <!-- Apartment List -->
    <h2>Apartment List</h2>
    <ul>
        <%
            List<Apartment> apartments = (List<Apartment>) request.getAttribute("apartments");

            if (apartments != null && !apartments.isEmpty()) {
                for (Apartment apartment : apartments) {
        %>
                    <li>
                        Apartment ID: <%= apartment.getId() %><br>
                        Price: <%= apartment.getPrice() %><br>
                        Name of Client: <%= apartment.getNameOfClient() %><br>
                        Status: <%= apartment.getApartmentStatus() %><br>

                        <form action="apartments" method="POST" style="display:inline;">
                            <input type="hidden" name="apartmentId" value="<%= apartment.getId() %>">
                            <input type="hidden" name="_method" value="DELETE">
                            <button type="submit">Delete</button>
                        </form>


                        <%-- Buttons for Reserve/Release --%>
                        <% if (apartment.getApartmentStatus() == ApartmentStatusEnum.FREE) { %>
                            <form action="reservations" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="reserve">
                                <input type="hidden" name="id" value="<%= apartment.getId() %>">
                                <input type="text" name="name" placeholder="Client Name" required>
                                <button type="submit">Reserve</button>
                            </form>
                        <% } else if (apartment.getApartmentStatus() == ApartmentStatusEnum.RESERVED) { %>
                            <form action="reservations" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="release">
                                <input type="hidden" name="id" value="<%= apartment.getId() %>">
                                <input type="text" name="name" placeholder="Client Name" required>
                                <button type="submit">Release</button>
                            </form>
                        <% } %>

                        <hr>
                    </li>
        <%
                }
            } else {
        %>
                <li>No apartments available.</li>
        <%
            }
        %>
    </ul>

    <hr>

    <!-- Pagination Controls -->
    <%
        int currentPage = request.getParameter("pageNumber") != null ? Integer.parseInt(request.getParameter("pageNumber")) : 1;
        int pageSize = request.getParameter("pageSize") != null ? Integer.parseInt(request.getParameter("pageSize")) : 5;

        boolean hasPrevious = currentPage > 1;
        boolean hasNext = apartments != null && apartments.size() == pageSize;
    %>

    <div>
        <form action="apartments" method="GET">
            <input type="hidden" name="sortBy" value="<%= request.getParameter("sortBy") != null ? request.getParameter("sortBy") : "none" %>">
            <input type="hidden" name="pageSize" value="<%= pageSize %>">

            <!-- Previous Button -->
            <% if (hasPrevious) { %>
                <button type="submit" name="pageNumber" value="<%= currentPage - 1 %>">Previous</button>
            <% } %>

            <!-- Current Page Display -->
            Page <%= currentPage %>

            <!-- Next Button -->
            <% if (hasNext) { %>
                <button type="submit" name="pageNumber" value="<%= currentPage + 1 %>">Next</button>
            <% } %>
        </form>
    </div>

</body>
</html>
