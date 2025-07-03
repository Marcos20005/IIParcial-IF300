<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String cedula = request.getParameter("cedula");

    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "erpalacios";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Funcionario Eliminado</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
<%
    if (cedula == null || cedula.trim().isEmpty()) {
%>
    <h3>Error: No se proporcionó una cédula válida.</h3>
<%
    } else {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "DELETE FROM oficinaregional WHERE IDempleado = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, cedula);
                    int filas = stmt.executeUpdate();
                    if (filas > 0) {
%>
    <p><strong>✅ Funcionario eliminado exitosamente.</strong></p>
<%
                    } else {
%>
    <p><strong>❌ No se pudo eliminar. El funcionario ya no existe.</strong></p>
<%
                    }
                }
            }
        } catch (Exception e) {
%>
    <p>Error al eliminar el funcionario: <%= e.getMessage() %></p>
<%
        }
    }
%>

<div class="botones">
    <form action="ConsultarOficina.jsp" method="post">
        <button type="submit">Volver a lista de oficinas</button>
    </form>
    <form action="Menu.jsp" method="get">
        <button type="submit">Volver al menú</button>
    </form>
</div>
</div>
</body>
</html>
