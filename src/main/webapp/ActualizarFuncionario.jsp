<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

   
    String idEmpleado = request.getParameter("idEmpleado");
    String nombre = request.getParameter("nombre");
    String cedulaFuncionario = request.getParameter("cedulaFuncionario");
    String solucion = request.getParameter("solucion");
    String lugar = request.getParameter("lugar");
    String direccion = request.getParameter("direccion");
    String telefono = request.getParameter("telefono");
    String cedulaCaso = request.getParameter("cedulaCaso");

    
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";

    boolean actualizado = false;
    String error = null;

    try {
        if (telefono == null || !telefono.matches("[0-9]+")) {
            throw new Exception("El campo 'Teléfono' solo debe contener números (ejemplo: 123456789)");
        }

        if (cedulaFuncionario == null || !cedulaFuncionario.matches("[0-9]+")) {
            throw new Exception("El campo 'Cédula' solo debe contener números (ejemplo: 123456789)");
        }

      
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "UPDATE oficinaregional SET Nombre=?, Cedula=?, Solucion=?, Lugar=?, Direccion=?, Telefono=? WHERE IDempleado=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, cedulaFuncionario);
                stmt.setString(3, solucion);
                stmt.setString(4, lugar);
                stmt.setString(5, direccion);
                stmt.setString(6, telefono);
                stmt.setString(7, idEmpleado);

              
                int filas = stmt.executeUpdate();
                if (filas > 0) {
                    actualizado = true;
                }
            }
        }
    } catch (Exception e) {
        error = e.getMessage();
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado Actualización</title>
    <link rel="stylesheet" href="estilo.css">
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
</head>
<body>
<div class="ventana">
<%
   
    if (actualizado) {
%>
    <h2><i class="fi fi-rr-check-circle"></i> Funcionario actualizado exitosamente.</h2>
<%
    } else if (error != null) {
%>
    <h3><i class="fi fi-rr-cross-circle"></i> Error al actualizar funcionario: <%= error %></h3>
<%
    } else {
%>
    <h3><i class="fi fi-rr-cross-circle"></i> No se pudo actualizar el funcionario.</h3>
<%
    }
%>
<div class="botones">
    <!-- Botón para volver a consultar oficinas -->
    <form action="ConsultarOficina.jsp" method="post">
        <button type="submit"><i class="fi fi-rr-rectangle-list"></i>Volver a oficinas</button>
    </form>
    <!-- Botón para volver al menú principal -->
    <form action="Menu.jsp" method="get">
        <button type="submit"><i class="fi fi-rr-undo"></i>Volver al menú</button>
    </form>
</div>
</div>
</body>
</html>
