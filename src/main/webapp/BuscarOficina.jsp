<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String idFuncionario = request.getParameter("cedula");

    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Información de Funcionario</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM oficinaregional WHERE IDempleado = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, idFuncionario);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
%>
    <h2>Información del Funcionario</h2>
    <p><strong>Nombre:</strong> <%= rs.getString("Nombre") %></p>
    <p><strong>Cédula:</strong> <%= rs.getString("Cedula") %></p>
    <p><strong>ID Empleado:</strong> <%= rs.getString("IDempleado") %></p>
    <p><strong>Teléfono:</strong> <%= rs.getString("Telefono") %></p>
    <p><strong>Dirección:</strong> <%= rs.getString("Direccion") %></p>
    <p><strong>Lugar:</strong> <%= rs.getString("Lugar") %></p>
    <p><strong>Fecha Atención:</strong> <%= rs.getString("FechaAtencion") %></p>
    <p><strong>Hora Atención:</strong> <%= rs.getString("HoraAtencion") %></p>
    <p><strong>Solución:</strong> <%= rs.getString("Solucion") %></p>
    <p><strong>Cédula del Caso Atendido:</strong> <%= rs.getString("CedulaCaso") %></p>
<%
                    } else {
%>
    <p>No se encontró un funcionario con ese ID.</p>
<%
                    }
                }
            }
        }
    } catch (Exception e) {
%>
    <p>Error al consultar: <%= e.getMessage() %></p>
<%
    }
%>
    <div class="botones">
        <form action="ConsultarOficina.jsp" method="post">
            <button type="submit">Volver</button>
        </form>
          <form action="Menu.jsp" method="get">
        <button type="submit">Volver al menú</button>
    </form>
</div>
</div>
</body>
</html>
