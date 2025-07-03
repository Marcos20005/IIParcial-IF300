<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

    String cedulaBuscada = request.getParameter("cedula");

    // Conexión a la base de datos
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "erpalacios";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado de búsqueda</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Información del caso</h2>
    <%
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

                // Buscar caso
                String sqlCaso = "SELECT * FROM caso WHERE Cedula = ?";
                try (PreparedStatement stmtCaso = conn.prepareStatement(sqlCaso)) {
                    stmtCaso.setString(1, cedulaBuscada);
                    try (ResultSet rs = stmtCaso.executeQuery()) {
                        if (rs.next()) {
    %>
                            <p><strong>Cédula:</strong> <%= rs.getString("Cedula") %></p>
                            <p><strong>Nombre:</strong> <%= rs.getString("Nombre") %></p>
                            <p><strong>Descripción:</strong> <%= rs.getString("Descripcion") %></p>
                            <p><strong>Fecha:</strong> <%= rs.getString("Fecha") %></p>
                            <p><strong>NumeroCelular:</strong> <%= rs.getString("NumeroCelular") %></p>
                            <p><strong>Direccion:</strong> <%= rs.getString("Direccion") %></p>
                            <p><strong>Edad:</strong> <%= rs.getString("Edad") %></p>
                            <p><strong>Genero:</strong> <%= rs.getString("Genero") %></p>
                            <p><strong>EstadoCivil:</strong> <%= rs.getString("EstadoCivil") %></p>
                            <p><strong>Ocupacion:</strong> <%= rs.getString("Ocupacion") %></p>
                            <p><strong>Nacionalidad:</strong> <%= rs.getString("Nacionalidad") %></p>
                            <p><strong>Agresor:</strong> <%= rs.getString("Agresor") %></p>
                            <p><strong>Relacion con Agresor:</strong> <%= rs.getString("RelacionAgresor") %></p>
                            <p><strong>Genero de Agresor:</strong> <%= rs.getString("GeneroAgresor") %></p>
                            <%
                                String tipoViolencia = rs.getString("TipoViolencia");
                            %>
                            <p><strong>Tipo de violencia:</strong> <%= tipoViolencia %></p>
                            <%
                                switch (tipoViolencia) {
                                    case "Violencia Económica":
                            %>
                                        <p><strong>TipoIngreso:</strong> <%= rs.getString("TipoIngreso") %></p>
                                        <p><strong>CantidadIngreso:</strong> <%= rs.getString("CantidadIngreso") %></p>
                            <%
                                        break;
                                    case "Violencia Física":
                            %>
                                        <p><strong>TipoLesion:</strong> <%= rs.getString("TipoLesion") %></p>
                                        <p><strong>AtencionMedica:</strong> <%= rs.getString("AtencionMedica") %></p>
                            <%
                                        break;
                                    case "Violencia Emocional":
                            %>
                                        <p><strong>ImpactoPsicologico:</strong> <%= rs.getString("ImpactoPsicologico") %></p>
                            <%
                                        break;
                                    case "Violencia Digital":
                            %>
                                        <p><strong>PlataformaDigital:</strong> <%= rs.getString("PlataformaDigital") %></p>
                            <%
                                        break;
                                    case "Violencia Sexual":
                            %>
                                        <p><strong>TipoAbusoSexual:</strong> <%= rs.getString("TipoAbusoSexual") %></p>
                            <%
                                        break;
                                    default:
                            %>
                                        <p><strong>Información adicional:</strong> No registrada</p>
                            <%
                                }
                            %>
    <%
                        } else {
    %>
                            <p>No se encontró un caso con esa cédula.</p>
    <%
                        }
                    }
                }

                // Buscar funcionario
                String sqlFunc = "SELECT * FROM oficinaregional WHERE CedulaCaso = ?";
                try (PreparedStatement stmtFunc = conn.prepareStatement(sqlFunc)) {
                    stmtFunc.setString(1, cedulaBuscada);
                    try (ResultSet rs = stmtFunc.executeQuery()) {
                        if (rs.next()) {
    %>
                            <h3>Funcionario asignado al caso</h3>
                            <p><strong>Nombre:</strong> <%= rs.getString("Nombre") %></p>
                            <p><strong>Cédula:</strong> <%= rs.getString("Cedula") %></p>
                            <p><strong>ID Empleado:</strong> <%= rs.getString("IDempleado") %></p>
                            <p><strong>Teléfono:</strong> <%= rs.getString("Telefono") %></p>
                            <p><strong>Dirección:</strong> <%= rs.getString("Direccion") %></p>
                            <p><strong>Lugar:</strong> <%= rs.getString("Lugar") %></p>
                            <p><strong>Fecha Atención:</strong> <%= rs.getString("FechaAtencion") %></p>
                            <p><strong>Hora Atención:</strong> <%= rs.getString("HoraAtencion") %></p>
                            <p><strong>Solución:</strong> <%= rs.getString("Solucion") %></p>
    <%
                        } else {
    %>
                            <p><em>No hay funcionario asignado a este caso.</em></p>
    <%
                        }
                    }
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
    %>
            <p>Error al consultar: <%= e.getMessage() %></p>
    <%
        }
    %>
    <div class="botones">
        <form action="ConsultarCasos.jsp" method="post"><button type="submit">Volver a lista de casos</button></form>
        <form action="Menu.jsp" method="get"><button type="submit">Volver al menú</button></form>
    </div>
</div>
</body>
</html>
