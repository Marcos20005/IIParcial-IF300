<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

    // Obtener la cédula enviada para buscar el caso a editar
    String cedulaBuscada = request.getParameter("cedula");

    // Parametros de conexión a la base de datos
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Caso</title>
     <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
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
                    // Asignar la cédula buscada al parámetro de la consulta
                    stmt.setString(1, cedulaBuscada);
                    ResultSet rs = stmt.executeQuery();

                    // Si existe el caso, obtener datos y mostrar formulario con valores
                    if (rs.next()) {
                        String nombre = rs.getString("Nombre");
                        String descripcion = rs.getString("Descripcion");
%>
    <h2>Editar Información del Caso</h2>
    <form action="ActualizarCaso.jsp" method="post">
         <!-- Campos ocultos para enviar la cedula, nombre y descripcion al actualizar -->
        <input type="hidden" name="cedula" value="<%= cedulaBuscada %>">

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="<%= nombre %>" required><br>

        <label for="descripcion">Descripción:</label>
        <textarea id="descripcion" name="descripcion" rows="4"><%= descripcion %></textarea><br>

        <div class="botones">
            <button type="submit"><i class="fi fi-rr-disk"></i>Guardar Cambios</button>
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

<!-- Botones para volver a la lista de casos o al menú principal -->
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
