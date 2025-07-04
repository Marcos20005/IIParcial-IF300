<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    // Obtener el parámetro 'login' enviado desde el formulario o petición
    String loginBuscado = request.getParameter("login");

    // Parámetros de conexión a la base de datos
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";

    String mensaje = "";
    boolean encontrado = false;

    // Variables para almacenar los datos del usuario
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
                            // Si se encuentra el usuario, se almacenan sus datos
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
     <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <%-- Mostrar mensaje de error si lo hay --%>
    <% if (!mensaje.isEmpty()) { %>
        <h3><%= mensaje %></h3>
    <% } else if (encontrado) { %>
        <%-- Mostrar formulario con datos precargados para editar usuario --%>
        <h2>Editar Información del Usuario</h2>
        <form action="ActualizarUsuario.jsp" method="post">
             <%-- Enviar el login oculto para identificar el usuario a actualizar --%>
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
                <button type="submit"><i class="fi fi-rr-disk"></i>Guardar Cambios</button>
            </div>
        </form>
    <% } %>

    <%-- Botones para volver a la lista de usuarios o al menú principal --%>
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
