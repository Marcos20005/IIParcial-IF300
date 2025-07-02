    <%@ page import="java.sql.*" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%
        request.setCharacterEncoding("UTF-8");

        String cedula = request.getParameter("cedula");

        String URL = "jdbc:mysql://localhost:3306/proyecto1";
        String USER = "root";
        String PASSWORD = "cRojas34";

        boolean mostrarFormularioEliminacion = false;
    %>
    <!DOCTYPE html>
    <html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Eliminar Caso</title>
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
                    String sql = "SELECT * FROM caso WHERE Cedula = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, cedula);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
    %>
        <h2>Información del caso que desea eliminar</h2>
        <p><strong>Cédula:</strong> <%= rs.getString("Cedula") %></p>
        <p><strong>Nombre:</strong> <%= rs.getString("Nombre") %></p>
        <p><strong>Descripción:</strong> <%= rs.getString("Descripcion") %></p>
        <p><strong>Fecha:</strong> <%= rs.getString("Fecha") %></p>
        <p><strong>NumeroCelular:</strong> <%= rs.getString("NumeroCelular") %></p>
        <p><strong>Dirección:</strong> <%= rs.getString("Direccion") %></p>
        <p><strong>Edad:</strong> <%= rs.getString("Edad") %></p>
        <p><strong>Género:</strong> <%= rs.getString("Genero") %></p>
        <p><strong>Estado Civil:</strong> <%= rs.getString("EstadoCivil") %></p>
        <p><strong>Ocupación:</strong> <%= rs.getString("Ocupacion") %></p>
        <p><strong>Nacionalidad:</strong> <%= rs.getString("Nacionalidad") %></p>
        <p><strong>Agresor:</strong> <%= rs.getString("Agresor") %></p>
        <p><strong>Relación con Agresor:</strong> <%= rs.getString("RelacionAgresor") %></p>
        <p><strong>Género del Agresor:</strong> <%= rs.getString("GeneroAgresor") %></p>
        <p><strong>Tipo de Violencia:</strong> <%= rs.getString("TipoViolencia") %></p>
    <%
                                // Mostrar campos específicos según tipo de violencia
                                String tipoViolencia = rs.getString("TipoViolencia");
                                switch (tipoViolencia) {
                                    case "Violencia Económica":
    %>
        <p><strong>Tipo de Ingreso:</strong> <%= rs.getString("TipoIngreso") %></p>
        <p><strong>Cantidad de Ingreso:</strong> <%= rs.getString("CantidadIngreso") %></p>
    <%
                                        break;
                                    case "Violencia Física":
    %>
        <p><strong>Tipo de Lesión:</strong> <%= rs.getString("TipoLesion") %></p>
        <p><strong>Atención Médica:</strong> <%= rs.getString("AtencionMedica") %></p>
    <%
                                        break;
                                    case "Violencia Emocional":
    %>
        <p><strong>Impacto Psicológico:</strong> <%= rs.getString("ImpactoPsicologico") %></p>
    <%
                                        break;
                                    case "Violencia Digital":
    %>
        <p><strong>Plataforma Digital:</strong> <%= rs.getString("PlataformaDigital") %></p>
    <%
                                        break;
                                    case "Violencia Sexual":
    %>
        <p><strong>Tipo de Abuso Sexual:</strong> <%= rs.getString("TipoAbusoSexual") %></p>
    <%
                                        break;
                                }
    %>
        <hr>
        <h3>¿Está seguro que desea eliminar este caso?</h3>
    <div class="botones">
        <form action="ConfirmarEliminarCaso.jsp" method="post">
            <input type="hidden" name="confirmar" value="true">
            <input type="hidden" name="cedula" value="<%= cedula %>">
            <button type="submit">Sí, eliminar caso</button>
        </form>
    </div>
    <%
                                mostrarFormularioEliminacion = true;
                            } else {
    %>
        <h3>No se encontró ningún caso con esa cédula.</h3>
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

        // Si el usuario ya confirmó la eliminación
        String confirmar = request.getParameter("confirmar");
        if ("true".equals(confirmar) && cedula != null && !cedula.trim().isEmpty()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    String sql = "DELETE FROM caso WHERE Cedula = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, cedula);
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
            <button type="submit">Volver a lista de casos</button>
        </form>
        <form action="Menu.jsp" method="get">
            <button type="submit">Volver al menú</button>
        </form>
    </div>
    </div>
    </body>
    </html>
