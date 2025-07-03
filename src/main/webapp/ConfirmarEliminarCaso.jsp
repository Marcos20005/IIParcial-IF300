<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

    // Obtener el parámetro 'cedula' enviado desde el formulario 
    String cedula = request.getParameter("cedula");

    // Parámetros de conexión a la base de datos
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Caso Eliminado</title>
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">

</head>
<body>
<div class="ventana">
<%
// Validar que se haya recibido una cédula válida para eliminar
    if (cedula == null || cedula.trim().isEmpty()) {
%>
    <h3>Error: No se proporcionó una cédula válida.</h3>
<%
    } else {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "DELETE FROM caso WHERE Cedula = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    // Asignar valor de cédula al parámetro SQL
                    stmt.setString(1, cedula);
                    // Ejecuta la actualización y obtiene el número de filas afectadas
                    int filas = stmt.executeUpdate();
                    if (filas > 0) {
%>
    <p><strong>✅ Caso eliminado exitosamente.</strong></p>
<%
                    } else {
%>
    <p><strong>❌ No se pudo eliminar. El caso ya no existe.</strong></p>
<%
                    }
                }
            }
        } catch (Exception e) {
%>
    <p>Error al eliminar el caso: <%= e.getMessage() %></p>
<%
        }
    }
%>

<div class="botones">
    <form action="ConsultarCasos.jsp" method="post">
        <button type="submit"><i class="fi fi-rr-rectangle-list"></i>Volver a lista de casos</button>
    </form>
    <form action="Menu.jsp" method="get">
        <button type="submit"><i class="fi fi-rr-undo"></i>Volver al menú</button>
    </form>
</div>
</div>
</body>
</html>
