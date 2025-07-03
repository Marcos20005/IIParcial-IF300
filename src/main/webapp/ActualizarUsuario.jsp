<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    request.setCharacterEncoding("UTF-8");

    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "erpalacios";

    String login = request.getParameter("login"); 
    String cedula = request.getParameter("cedula");
    String nombre1 = request.getParameter("nombre1");
    String nombre2 = request.getParameter("nombre2");
    String apellido1 = request.getParameter("apellido1");
    String apellido2 = request.getParameter("apellido2");
    String clave = request.getParameter("clave");

    String mensaje = "";

    if (login == null || login.isEmpty()) {
        mensaje = "Error: El login es obligatorio para actualizar el usuario.";
    } else {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "UPDATE usuario SET cedula = ?, nombre1 = ?, nombre2 = ?, apellido1 = ?, apellido2 = ?, clave = ? WHERE login = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, cedula);
                    stmt.setString(2, nombre1);
                    stmt.setString(3, nombre2);
                    stmt.setString(4, apellido1);
                    stmt.setString(5, apellido2);
                    stmt.setString(6, clave);
                    stmt.setString(7, login);

                    int filasActualizadas = stmt.executeUpdate();

                    if (filasActualizadas > 0) {
                        mensaje = "✅ Usuario actualizado correctamente.";
                    } else {
                        mensaje = "❌ No se pudo actualizar el usuario. No se encontró el login.";
                    }
                }
            }
        } catch (Exception e) {
            mensaje = "Error al actualizar la base de datos: " + e.getMessage();
        }
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Actualizar Usuario</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Actualizar Información del Usuario</h2>
    <p><%= mensaje %></p>

    <div class="botones">
        <form action="ConsultarUsuarios.jsp" method="post" style="display:inline;">
            <button type="submit">Volver a lista de usuarios</button>
        </form>

        <form action="Menu.jsp" method="get" style="display:inline; margin-left:10px;">
            <button type="submit">Volver al menú</button>
        </form>
    </div>
</div>
</body>
</html>
