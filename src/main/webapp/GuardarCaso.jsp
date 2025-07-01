<%@ page import="java.sql.*, java.time.*, javax.servlet.http.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%
    request.setCharacterEncoding("UTF-8");

    // Parámetros del formulario
    String descripcion = request.getParameter("txtDescripcionDenuncia");
    String nombre = request.getParameter("txtNombre");
    int edad = Integer.parseInt(request.getParameter("txtedad"));
    String direccion = request.getParameter("txtDireccion");
    String ocupacion = request.getParameter("txtOcupacion");
    String estadoCivil = request.getParameter("boxEstado");
    String cedula = request.getParameter("txtCedula");
    String celular = request.getParameter("txtCelular");
    String genero = request.getParameter("genero");
    String nacionalidad = request.getParameter("txtNacionalidad");
    String tipoViolencia = request.getParameter("tipoViolencia");

    LocalDateTime fechaActual = LocalDateTime.now();
    LocalDate soloFecha = fechaActual.toLocalDate();

    String url = "jdbc:mysql://localhost:3306/proyecto1";
    String user = "root";
    String password = "cRojas34";

    boolean exito = false;
    String mensaje = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO caso (Descripcion, Nombre, Edad, Direccion, Ocupacion, EstadoCivil, Cedula, NumeroCelular, Genero, Nacionalidad, TipoViolencia, Resuelto, Fecha) "
                    + "VALUES ('" + descripcion + "', '" + nombre + "', " + edad + ", '" + direccion + "', '" + ocupacion + "', '" + estadoCivil + "', '"
                    + cedula + "', '" + celular + "', '" + genero + "', '" + nacionalidad + "', '" + tipoViolencia + "', 'False', '" + soloFecha + "')";

            int filasAfectadas = stmt.executeUpdate(sql);

            if (filasAfectadas > 0) {
                exito = true;

                // Guardar en sesión
                session.setAttribute("CedulaCaso", cedula);
                session.setAttribute("tipoViolencia", tipoViolencia);

                // Redirigir según el tipo de violencia
                if ("Violencia Digital".equals(tipoViolencia)) {
                    response.sendRedirect("vDigital.jsp");
                    return;
                } else if ("Violencia Económica".equals(tipoViolencia)) {
                    response.sendRedirect("vEconomica.jsp");
                    return;
                } else if ("Violencia Emocional".equals(tipoViolencia)) {
                    response.sendRedirect("vEmocional.jsp");
                    return;
                } else if ("Violencia Física".equals(tipoViolencia)) {
                    response.sendRedirect("vFisica.jsp");
                    return;
                } else if ("Violencia Sexual".equals(tipoViolencia)) {
                    response.sendRedirect("vSexual.jsp");
                    return;
                }

                mensaje = "Caso guardado exitosamente.";
            } else {
                mensaje = "Error al guardar el caso.";
            }

        } catch (SQLException e) {
            mensaje = "Error SQL: " + e.getMessage();
        }

    } catch (ClassNotFoundException e) {
        mensaje = "Error al cargar el driver de la base de datos.";
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado del caso</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
    <div class="ventana">
        <h2>Resultado</h2>
        <p><strong><%= mensaje %></strong></p>

        <form action="Menu.jsp" method="get">
            <button type="submit">Volver al menú</button>
        </form>
    </div>
</body>
</html>
