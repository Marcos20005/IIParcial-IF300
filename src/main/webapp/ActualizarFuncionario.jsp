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
    String PASSWORD = "erpalacios";

    boolean actualizado = false;
    String error = null;

    try {
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
</head>
<body>
<div class="ventana">
<%
    if (actualizado) {
%>
    <h2>✅ Funcionario actualizado exitosamente.</h2>
<%
    } else if (error != null) {
%>
    <h3>❌ Error al actualizar funcionario: <%= error %></h3>
<%
    } else {
%>
    <h3>❌ No se pudo actualizar el funcionario.</h3>
<%
    }
%>
<div class="botones">
    <form action="ConsultarOficina.jsp" method="post">
        <button type="submit">Volver a oficinas</button>
    </form>
      <form action="Menu.jsp" method="get">
        <button type="submit">Volver al menú</button>
    </form>
</div>
</div>
</body>
</html>
