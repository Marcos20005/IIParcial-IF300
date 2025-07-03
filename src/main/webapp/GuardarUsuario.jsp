<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

    // Captura de parámetros del formulario 
    String cedula = request.getParameter("cedula");
    String nombre1 = request.getParameter("nombre1");
    String nombre2 = request.getParameter("nombre2");
    String apellido1 = request.getParameter("apellido1");
    String apellido2 = request.getParameter("apellido2");
    String login = request.getParameter("login");
    String clave = request.getParameter("clave");

    String mensaje = null;

        // Validar que los campos obligatorios no vengan nulos
    if (cedula != null && nombre1 != null && apellido1 != null && login != null && clave != null) {
        String URL = "jdbc:mysql://localhost:3306/proyecto1";
        String USER = "root";
        String PASSWORD = "cRojas34";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "INSERT INTO usuario (cedula, nombre1, nombre2, apellido1, apellido2, login, clave) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    // Establecer los parámetros de la consulta
                    stmt.setString(1, cedula);
                    stmt.setString(2, nombre1);
                    stmt.setString(3, nombre2);
                    stmt.setString(4, apellido1);
                    stmt.setString(5, apellido2);
                    stmt.setString(6, login);
                    stmt.setString(7, clave);

                    int filas = stmt.executeUpdate();
                    if (filas > 0) {
                        mensaje = "✅ Usuario guardado exitosamente.";
                        response.sendRedirect("Menu.jsp");
                        return;
                    } else {
                        mensaje = "❌ No se pudo guardar el usuario.";
                    }
                }
            }
        } catch (Exception e) {
            mensaje = "⚠️ Error: " + e.getMessage();
        }
    } else {
        mensaje = "⚠️ Por favor complete todos los campos obligatorios.";
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Guardar Usuario</title>
    <!-- Estilos e íconos -->
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Resultado de registro</h2>
    <p><%= mensaje %></p>

    <div class="botones">
        <form action="vUsuario.jsp" method="get" style="display:inline;">
            <button type="submit">🔁 Intentar de nuevo</button>
        </form>

        <form action="Menu.jsp" method="get" style="display:inline; margin-left:10px;">
            <button type="submit"><i class="fi fi-rr-disk"> Volver al menú</button>
        </form>
    </div>
</div>
</body>
</html>
