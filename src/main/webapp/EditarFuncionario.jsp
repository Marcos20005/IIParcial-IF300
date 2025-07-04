<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

    // Obtener la cédula del funcionario a editar
    String idFuncionario = request.getParameter("cedula");

    // Credenciales de la base de datos
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "erpalacios";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Funcionario</title>
     <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Editar Funcionario</h2>
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM oficinaregional WHERE IDempleado = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Asignar el ID del funcionario al parámetro de la consulta
                stmt.setString(1, idFuncionario);
                try (ResultSet rs = stmt.executeQuery()) {
                    // Verificar si se encontró el funcionario
                    if (rs.next()) {
%>
<!-- Formulario para editar los datos del funcionario con los valores actuales precargados -->
    <form action="ActualizarFuncionario.jsp" method="post">
        <input type="hidden" name="idEmpleado" value="<%= rs.getString("IDempleado") %>">

        <label>Nombre:</label>
        <input type="text" name="nombre" value="<%= rs.getString("Nombre") %>" required><br>

        <label>Cédula:</label>
        <input type="text" name="cedulaFuncionario" value="<%= rs.getString("Cedula") %>" required><br>

        <label>Teléfono:</label>
        <input type="text" name="telefono" value="<%= rs.getString("Telefono") %>" required><br>

        <label>Dirección:</label>
        <input type="text" name="direccion" value="<%= rs.getString("Direccion") %>" required><br>

        <label>Lugar:</label>
        <input type="text" name="lugar" value="<%= rs.getString("Lugar") %>" required><br>

        <label>Solución propuesta:</label>
        <textarea name="solucion" required><%= rs.getString("Solucion") %></textarea><br>

        <input type="hidden" name="cedulaCaso" value="<%= rs.getString("CedulaCaso") %>">

        <div class="botones">
            <button type="submit"><i class="fi fi-rr-disk"></i>Guardar cambios</button>
    </form>

    
</div>
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
    <p>Error: <%= e.getMessage() %></p>
<%
    }
%>
<div class="botones">
    <!-- Botones para volver a la lista de oficinas o al menú principal -->
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
