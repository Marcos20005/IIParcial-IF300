<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Parámetros de conexión a la base de datos
    String url = "jdbc:mysql://localhost:3306/proyecto1";
    String user = "root";
    String password = "cRojas34";

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Casos Registrados</title>
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
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
        // Consulta para obtener cédula, nombre y descripción de los casos
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
        // Cerrar recursos para evitar fugas de memoria
        if (rs != null) try { rs.close(); } catch (Exception e) {}
        if (stmt != null) try { stmt.close(); } catch (Exception e) {}
        if (conn != null) try { conn.close(); } catch (Exception e) {}
    }
%>
        </textarea>

        <label for="cedula">Ingrese cédula del caso</label>
        <input type="text" id="cedula" name="cedula">

        <div class="botones">
               <!-- Botón para ingresar un nuevo caso -->
            <form action="AgregarCaso.jsp" method="post" style="display:inline;">
                <button type="submit"> <i class="fi fi-rr-add-document"></i>Ingresar nuevo caso</button>
            </form>

            <!-- Botones para buscar, editar y eliminar casos -->
            <form action="BuscarCaso.jsp" method="post">
                <input type="hidden" name="cedula" id="cedulaBuscar">
                <button type="submit" onclick="document.getElementById('cedulaBuscar').value = document.getElementById('cedula').value;"> <i class="fi fi-rr-search"></i> Buscar</button>
            </form>

            <form action="EditarCaso.jsp" method="post">
                <input type="hidden" name="cedula" id="cedulaEditar">
                <button type="submit" onclick="document.getElementById('cedulaEditar').value = document.getElementById('cedula').value;">  <i class="fi fi-rr-edit"></i> Editar</button>
            </form>

            <form action="EliminarCaso.jsp" method="post">
                <input type="hidden" name="cedula" id="cedulaEliminar">
                <button type="submit" onclick="document.getElementById('cedulaEliminar').value = document.getElementById('cedula').value; return confirmarEliminacion();">  <i class="fi fi-rr-trash"></i> Eliminar</button>
            </form>
            <form action="Menu.jsp" method="post">
                <button type="submit"><i class="fi fi-rr-undo"></i>Volver a Menu </button>
            </form>
        </div>

        <script>
            // Función que pregunta confirmación antes de eliminar un caso
            function confirmarEliminacion() {
                return confirm('¿Estás seguro de que desea eliminar este caso?');
            }
        </script>
    </div>
</body>
</html>
