<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    String loginBuscado = request.getParameter("login");
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";

    String mensaje = "";
    String cedula = "", nombre1 = "", nombre2 = "", apellido1 = "", apellido2 = "", login = "", clave = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM usuario WHERE login = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, loginBuscado);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        cedula = rs.getString("cedula");
                        nombre1 = rs.getString("nombre1");
                        nombre2 = rs.getString("nombre2");
                        apellido1 = rs.getString("apellido1");
                        apellido2 = rs.getString("apellido2");
                        login = rs.getString("login");
                        clave = rs.getString("clave");
                    } else {
                        mensaje = "No se encontró un usuario con ese ID.";
                    }
                }
            }
        }
    } catch (SQLException e) {
        mensaje = "Error en la base de datos: " + e.getMessage();
    } catch (ClassNotFoundException e) {
        mensaje = "No se pudo cargar el driver JDBC.";
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado de búsqueda de usuario</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Información del usuario</h2>

    <% if (!mensaje.isEmpty()) { %>
        <p><%= mensaje %></p>
    <% } else { %>
        <p><strong>Cédula:</strong> <%= cedula %></p>
        <p><strong>Nombre 1:</strong> <%= nombre1 %></p>
        <p><strong>Nombre 2:</strong> <%= nombre2 %></p>
        <p><strong>Apellido 1:</strong> <%= apellido1 %></p>
        <p><strong>Apellido 2:</strong> <%= apellido2 %></p>
        <p><strong>Login:</strong> <%= login %></p>
        <p><strong>Clave:</strong> <%= clave %></p>
    <% } %>

    <div class="botones">
        <form action="ConsultarUsuarios.jsp" method="post" style="display:inline;">
            <button type="submit">Volver a lista de usuarios</button>
        </form>

        <form action="Menu.jsp" method="get" style="display:inline;">
            <button type="submit">Volver al menú</button>
        </form>
    </div>
</div>
</body>
</html>
