<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    request.setCharacterEncoding("UTF-8");

    String cedula = request.getParameter("cedula");
    String nombre = request.getParameter("nombre");
    String descripcion = request.getParameter("descripcion");

    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Actualizar Caso</title>
    <link rel="stylesheet" href="estilo.css">
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
</head>
<body>
<div class="ventana">
<h2>Actualizar Información del Caso</h2>
<%

    try {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new Exception("La cédula es obligatoria para actualizar el caso");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new Exception("La descripción no puede estar vacía");
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "UPDATE caso SET Nombre = ?, Descripcion = ? WHERE Cedula = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, descripcion);
                stmt.setString(3, cedula);

                int filasActualizadas = stmt.executeUpdate();

                if (filasActualizadas > 0) {
%>
    <p><i class="fi fi-rr-check-circle"></i> Caso actualizado correctamente.</p>
<%
                } else {
%>
    <p><i class="fi fi-rr-cross-circle"></i> No se pudo actualizar el caso. No se encontró la cédula.</p>
<%
                }
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
%>
    <p><i class="fi fi-rr-cross-circle"></i> Error al actualizar la base de datos: <%= e.getMessage() %></p>
<%
    } catch (Exception ex) {
%>
    <p><i class="fi fi-rr-exclamation"></i> Error de validación: <%= ex.getMessage() %></p>
<%
    }
%>

<div class="botones">
    <form action="ConsultarCasos.jsp" method="post" style="display:inline;">
        <button type="submit"><i class="fi fi-rr-rectangle-list"></i>Volver a lista de casos</button>
    </form>
    <form action="Menu.jsp" method="get" style="display:inline; margin-left:10px;">
        <button type="submit"><i class="fi fi-rr-undo"></i>Volver al menú</button>
    </form>
</div>
</div>
</body>
</html>
