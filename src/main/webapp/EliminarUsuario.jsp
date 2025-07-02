<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    request.setCharacterEncoding("UTF-8");

    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";

    String login = request.getParameter("login");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Eliminar Usuario</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
<%
    if (login == null || login.trim().isEmpty()) {
%>
    <h3>❌ Error: No se proporcionó un ID de usuario válido.</h3>
<%
    } else {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM usuario WHERE login = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, login);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
%>
    <h2>Información del usuario a eliminar</h2>
    <p><strong>Cédula:</strong> <%= rs.getString("cedula") %></p>
    <p><strong>Nombre 1:</strong> <%= rs.getString("nombre1") %></p>
    <p><strong>Nombre 2:</strong> <%= rs.getString("nombre2") %></p>
    <p><strong>Apellido 1:</strong> <%= rs.getString("apellido1") %></p>
    <p><strong>Apellido 2:</strong> <%= rs.getString("apellido2") %></p>
    <p><strong>Login:</strong> <%= rs.getString("login") %></p>
    <p><strong>Clave:</strong> <%= rs.getString("clave") %></p>
    <hr>
    <h3>¿Está seguro que desea eliminar este usuario?</h3>
    <div class="botones">
        <form action="ConfirmarEliminarUsuario.jsp" method="post">
            <input type="hidden" name="login" value="<%= login %>">
            <input type="hidden" name="confirmar" value="true">
            <button type="submit">Sí, eliminar usuario</button>
        </form>
    </div>
<%
                        } else {
%>
    <h3>⚠️ No se encontró ningún usuario con ese login.</h3>
<%
                        }
                    }
                }
            }
        } catch (Exception e) {
%>
    <p>❌ Error al acceder a la base de datos: <%= e.getMessage() %></p>
<%
        }
    }
%>
<div class="botones">
    <form action="ConsultarUsuarios.jsp" method="post">
        <button type="submit">Volver a lista de usuarios</button>
    </form>
    <form action="Menu.jsp" method="get">
        <button type="submit">Volver al menú</button>
    </form>
</div>
</div>
</body>
</html>
