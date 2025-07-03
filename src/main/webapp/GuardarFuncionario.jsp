<%@ page import="java.sql.*, java.time.LocalDate, java.time.LocalTime, java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

    // Parámetros recibidos del formulario
    String nombre = request.getParameter("nombre");
    String cedulaFuncionario = request.getParameter("cedulaFuncionario");
    String idEmpleado = request.getParameter("idEmpleado");
    String solucion = request.getParameter("solucion");

    String lugar = request.getParameter("lugar");
    String telefono = request.getParameter("telefono");
    String direccion = request.getParameter("direccion");
    String cedulaCaso = request.getParameter("cedulaCaso");

    // Obtiene la fecha y hora actual
    LocalDate fechaActual = LocalDate.now();
    LocalTime horaActual = LocalTime.now();

    String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String horaFormateada = horaActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

    // Parámetros de conexión a la base de datos
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Guardar Funcionario</title>
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

                // Preparar consulta SQL para insertar el nuevo funcionario
            String sql = "INSERT INTO oficinaregional (IDempleado, Lugar, Direccion, Telefono, Nombre, Cedula, HoraAtencion, FechaAtencion, Solucion, CedulaCaso) VALUES ("
                    + "'" + idEmpleado + "', "
                    + "'" + lugar + "', "
                    + "'" + direccion + "', "
                    + "'" + telefono + "', "
                    + "'" + nombre + "', "
                    + "'" + cedulaFuncionario + "', "
                    + "'" + horaFormateada + "', "
                    + "'" + fechaFormateada + "', "
                    + "'" + solucion + "', "
                    + "'" + cedulaCaso + "')";

                    // Ejecutar la inserción
            int filasAfectadas = stmt.executeUpdate(sql);

            if (filasAfectadas > 0) {
                response.sendRedirect("ConsultarOficina.jsp");
            } else {
%>
    <h3>No se pudo registrar el funcionario.</h3>
<%
            }
        }
    } catch (Exception e) {
%>
    <p>Error al guardar funcionario: <%= e.getMessage() %></p>
<%
    }
%>
</div>
</body>
</html>
