<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url = "jdbc:mysql://localhost:3306/proyecto1";
    String user = "root";
    String password = "erpalacios";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Casos Registrados</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
    <div class="ventana">
        <h2>Casos Registrados</h2>

        <label for="areaCasos">Lista de casos</label>
        <textarea id="areaCasos" rows="8" readonly>
<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, password);
        String sql = "SELECT Cedula, Nombre, Descripcion FROM caso";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        while (rs.next()) {
            String cedula = rs.getString("Cedula");
            String nombre = rs.getString("Nombre");
            String descripcion = rs.getString("Descripcion");

            out.println("Cédula: " + cedula + ", Nombre: " + nombre + ", Descripción: " + descripcion);
            out.println(); // salto de línea
        }
    } catch (Exception e) {
        out.println("Error al consultar los casos: " + e.getMessage());
    } finally {
        if (rs != null) try { rs.close(); } catch (Exception e) {}
        if (stmt != null) try { stmt.close(); } catch (Exception e) {}
        if (conn != null) try { conn.close(); } catch (Exception e) {}
    }
%>
        </textarea>

        <label for="cedula">Ingrese cédula del caso</label>
        <input type="text" id="cedula" name="cedula">

        <div class="botones">
            <form action="AgregarCaso.jsp" method="post" style="display:inline;">
                <button type="submit">Ingresar nuevo caso</button>
            </form>

            <form action="BuscarCaso.jsp" method="post">
                <input type="hidden" name="cedula" id="cedulaBuscar">
                <button type="submit" onclick="document.getElementById('cedulaBuscar').value = document.getElementById('cedula').value;">Buscar</button>
            </form>

            <form action="EditarCaso.jsp" method="post">
                <input type="hidden" name="cedula" id="cedulaEditar">
                <button type="submit" onclick="document.getElementById('cedulaEditar').value = document.getElementById('cedula').value;">Editar</button>
            </form>

            <form action="EliminarCaso.jsp" method="post">
                <input type="hidden" name="cedula" id="cedulaEliminar">
                <button type="submit" onclick="document.getElementById('cedulaEliminar').value = document.getElementById('cedula').value; return confirmarEliminacion();">Eliminar</button>
            </form>
            <form action="Menu.jsp" method="post">
                <button type="submit">Volver a Menu </button>
            </form>
        </div>

        <script>
            function confirmarEliminacion() {
                return confirm('¿Estás seguro de que desea eliminar este caso?');
            }
        </script>
    </div>
</body>
</html>
