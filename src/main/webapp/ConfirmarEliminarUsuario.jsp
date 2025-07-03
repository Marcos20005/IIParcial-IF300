<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

    String login = request.getParameter("login");
    String mensaje = "";

    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "erpalacios";

    if (login == null || login.trim().isEmpty()) {
        mensaje = "❌ Error: No se proporcionó un login válido para eliminar.";
    } else {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "DELETE FROM usuario WHERE login = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, login);
                    int filasAfectadas = stmt.executeUpdate();
                    if (filasAfectadas > 0) {
                        mensaje = "✅ Usuario con login <strong>" + login + "</strong> eliminado exitosamente.";
                    } else {
                        mensaje = "⚠️ No se encontró ningún usuario con ese login.";
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            mensaje = "❌ Error al eliminar el usuario: " + e.getMessage();
        }
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Confirmar Eliminación de Usuario</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Eliminar Usuario</h2>
    <p><%= mensaje %></p>

    <div class="botones">
        <form action="ConsultarUsuarios.jsp" method="post">
            <button type="submit">Volver a lista de usuarios</button>
        </form>
        <form action="Menu.html" method="get">
            <button type="submit">Volver al menú</button>
        </form>
    </div>
</div>
</body>
</html>
