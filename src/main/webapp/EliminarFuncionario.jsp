<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String cedula = request.getParameter("cedula");

    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Eliminar Funcionario</title>
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
<%
    if (cedula == null || cedula.trim().isEmpty()) {
%>
    <h3>Error: No se proporcionó un ID de funcionario válido.</h3>
<%
    } else {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM oficinaregional WHERE IDempleado = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, cedula);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
%>
    <h2>Información del funcionario que desea eliminar</h2>
    <p><strong>Nombre:</strong> <%= rs.getString("Nombre") %></p>
    <p><strong>Cédula:</strong> <%= rs.getString("Cedula") %></p>
    <p><strong>ID Empleado:</strong> <%= rs.getString("IDempleado") %></p>
    <p><strong>Teléfono:</strong> <%= rs.getString("Telefono") %></p>
    <p><strong>Dirección:</strong> <%= rs.getString("Direccion") %></p>
    <p><strong>Lugar:</strong> <%= rs.getString("Lugar") %></p>
    <p><strong>Fecha Atención:</strong> <%= rs.getString("FechaAtencion") %></p>
    <p><strong>Hora Atención:</strong> <%= rs.getString("HoraAtencion") %></p>
    <p><strong>Solución:</strong> <%= rs.getString("Solucion") %></p>
    <p><strong>Cédula del Caso:</strong> <%= rs.getString("CedulaCaso") %></p>
    <hr>
    <h3>¿Está seguro que desea eliminar este funcionario?</h3>
    <div class="botones">
        <form action="ConfirmarEliminarFuncionario.jsp" method="post">
            <input type="hidden" name="confirmar" value="true">
            <input type="hidden" name="cedula" value="<%= cedula %>">
            <button type="submit"><i class="fi fi-rr-trash"></i>Sí, eliminar funcionario</button>
        </form>
    </div>
<%
                        } else {
%>
    <h3>No se encontró ningún funcionario con ese ID.</h3>
<%
                        }
                    }
                }
            }
        } catch (Exception e) {
%>
    <p>Error al acceder a la base de datos: <%= e.getMessage() %></p>
<%
        }
    }
%>
<div class="botones">
    <form action="ConsultarOficina.jsp" method="post">
        <button type="submit"><i class="fi fi-rr-rectangle-list"></i>Volver a lista de oficinas</button>
    </form>
    <form action="Menu.jsp" method="get">
        <button type="submit"><i class="fi fi-rr-undo"></i>Volver al menú</button>
    </form>
</div>
</div>
</body>
</html>
