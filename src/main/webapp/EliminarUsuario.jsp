<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    request.setCharacterEncoding("UTF-8");

    // Parámetros de conexión a la base de datos
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";

    // Obtener el login del usuario a eliminar desde la solicitud
    String login = request.getParameter("login");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Eliminar Usuario</title>
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
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
                        // Verificar si se encontró el usuario
                        if (rs.next()) {
%>
    <h2>Información del usuario a eliminar</h2>
    <!-- Mostrar los datos del usuario -->
    <p><strong>Cédula:</strong> <%= rs.getString("cedula") %></p>
    <p><strong>Nombre 1:</strong> <%= rs.getString("nombre1") %></p>
    <p><strong>Nombre 2:</strong> <%= rs.getString("nombre2") %></p>
    <p><strong>Apellido 1:</strong> <%= rs.getString("apellido1") %></p>
    <p><strong>Apellido 2:</strong> <%= rs.getString("apellido2") %></p>
    <p><strong>Login:</strong> <%= rs.getString("login") %></p>
    <p><strong>Clave:</strong> <%= rs.getString("clave") %></p>
    <hr>
    <h3>¿Está seguro que desea eliminar este usuario?</h3>

    <!-- Formulario para confirmar la eliminación del usuario -->
    <div class="botones">
        <form action="ConfirmarEliminarUsuario.jsp" method="post">
            <input type="hidden" name="login" value="<%= login %>">
            <input type="hidden" name="confirmar" value="true">
            <button type="submit"><i class="fi fi-rr-trash"></i>Sí, eliminar usuario</button>
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
        <button type="submit"><i class="fi fi-rr-rectangle-list"></i>Volver a lista de usuarios</button>
    </form>
    <form action="Menu.jsp" method="get">
        <button type="submit"><i class="fi fi-rr-undo"></i>Volver al menú</button>
    </form>
</div>
</div>
</body>
</html>
