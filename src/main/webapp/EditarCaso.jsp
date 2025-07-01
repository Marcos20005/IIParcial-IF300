<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

    String cedulaBuscada = request.getParameter("cedula");

    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "erpalacios";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Caso</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
<%
    if (cedulaBuscada == null || cedulaBuscada.isEmpty()) {
%>
    <h3>Error: No se proporcionó una cédula válida.</h3>
<%
    } else {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT Nombre, Descripcion FROM caso WHERE Cedula = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, cedulaBuscada);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String nombre = rs.getString("Nombre");
                        String descripcion = rs.getString("Descripcion");
%>
    <h2>Editar Información del Caso</h2>
    <form action="ActualizarCaso.jsp" method="post">
        <input type="hidden" name="cedula" value="<%= cedulaBuscada %>">

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="<%= nombre %>" required><br>

        <label for="descripcion">Descripción:</label>
        <textarea id="descripcion" name="descripcion" rows="4"><%= descripcion %></textarea><br>

        <div class="botones">
            <button type="submit">Guardar Cambios</button>
        </div>
    </form>
<%
                    } else {
%>
    <h3>No se encontró un caso con la cédula ingresada.</h3>
<%
                    }
                }
            }
        } catch (SQLException e) {
%>
    <p>Error al acceder a la base de datos: <%= e.getMessage() %></p>
<%
        } catch (ClassNotFoundException e) {
%>
    <p>Error al cargar el driver JDBC.</p>
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
