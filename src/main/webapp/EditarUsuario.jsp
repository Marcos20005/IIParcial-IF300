<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    String loginBuscado = request.getParameter("login");

    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";

    String mensaje = "";
    boolean encontrado = false;

    String cedula = "", nombre1 = "", nombre2 = "", apellido1 = "", apellido2 = "", clave = "";

    if (loginBuscado != null && !loginBuscado.isEmpty()) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM usuario WHERE login = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, loginBuscado);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            encontrado = true;
                            cedula = rs.getString("cedula");
                            nombre1 = rs.getString("nombre1");
                            nombre2 = rs.getString("nombre2");
                            apellido1 = rs.getString("apellido1");
                            apellido2 = rs.getString("apellido2");
                            clave = rs.getString("clave");
                        } else {
                            mensaje = "No se encontró un usuario con ese ID.";
                        }
                    }
                }
            }
        } catch (SQLException e) {
            mensaje = "Error al acceder a la base de datos: " + e.getMessage();
        } catch (ClassNotFoundException e) {
            mensaje = "Error al cargar el driver JDBC.";
        }
    } else {
        mensaje = "Error: No se proporcionó un ID válido.";
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <% if (!mensaje.isEmpty()) { %>
        <h3><%= mensaje %></h3>
    <% } else if (encontrado) { %>
        <h2>Editar Información del Usuario</h2>
        <form action="ActualizarUsuario.jsp" method="post">
            <input type="hidden" name="login" value="<%= loginBuscado %>">

            <label for="cedula">Cédula:</label>
            <input type="text" id="cedula" name="cedula" value="<%= cedula %>" required><br>

            <label for="nombre1">Nombre 1:</label>
            <input type="text" id="nombre1" name="nombre1" value="<%= nombre1 %>" required><br>

            <label for="nombre2">Nombre 2:</label>
            <input type="text" id="nombre2" name="nombre2" value="<%= nombre2 %>"><br>

            <label for="apellido1">Apellido 1:</label>
            <input type="text" id="apellido1" name="apellido1" value="<%= apellido1 %>" required><br>

            <label for="apellido2">Apellido 2:</label>
            <input type="text" id="apellido2" name="apellido2" value="<%= apellido2 %>"><br>

            <label for="clave">Clave:</label>
            <input type="password" id="clave" name="clave" value="<%= clave %>" required><br>

            <div class="botones">
                <button type="submit">Guardar Cambios</button>
            </div>
        </form>
    <% } %>

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
