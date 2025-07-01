<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "erpalacios";

    Connection conn = null;
    PreparedStatement stmt1 = null, stmt2 = null;
    ResultSet rs1 = null, rs2 = null;

    StringBuilder textoOficinas = new StringBuilder();
    StringBuilder textoCasos = new StringBuilder();

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USER, PASSWORD);

        String sqlOficinas = "SELECT IDempleado, Nombre, Lugar FROM oficinaregional";
        stmt1 = conn.prepareStatement(sqlOficinas);
        rs1 = stmt1.executeQuery();

        while (rs1.next()) {
            textoOficinas.append("ID Empleado: ").append(rs1.getString("IDempleado"))
                         .append(", Nombre: ").append(rs1.getString("Nombre"))
                         .append(", Lugar: ").append(rs1.getString("Lugar"))
                         .append("\n");
        }

      String sqlCasos = "SELECT c.Cedula FROM caso c LEFT JOIN oficinaregional o ON c.Cedula = o.CedulaCaso WHERE o.CedulaCaso IS NULL";
stmt2 = conn.prepareStatement(sqlCasos);
rs2 = stmt2.executeQuery();

while (rs2.next()) {
    textoCasos.append(rs2.getString("Cedula")).append("\n");
}
    } catch (Exception e) {
        textoOficinas.append("Error al consultar: ").append(e.getMessage());
    } finally {
        if (rs1 != null) try { rs1.close(); } catch (Exception e) {}
        if (rs2 != null) try { rs2.close(); } catch (Exception e) {}
        if (stmt1 != null) try { stmt1.close(); } catch (Exception e) {}
        if (stmt2 != null) try { stmt2.close(); } catch (Exception e) {}
        if (conn != null) try { conn.close(); } catch (Exception e) {}
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Oficinas con respuestas registradas</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Oficinas con respuestas registradas</h2>

    <label for="areaOficinas">Funcionarios registrados</label>
    <textarea id="areaOficinas" rows="5" readonly><%= textoOficinas.toString() %></textarea>
    <br><br>

    <label for="areaCasos">Cédulas de casos registrados</label>
    <textarea id="areaCasos" rows="5" readonly><%= textoCasos.toString() %></textarea>
    <br><br>

    <label for="cedulaCaso">Ingrese cédula del caso para asignar funcionario o ID y seleccione función</label><br>
    <input type="text" id="cedulaCaso" name="cedulaCaso"><br><br>

    <div class="botones">
        <form action="vFuncionario.jsp" method="post" style="display:inline; margin-left:10px;">
            <input type="hidden" name="cedulaCaso" id="cedulaCasoOculta">
            <button type="submit" onclick="document.getElementById('cedulaCasoOculta').value = document.getElementById('cedulaCaso').value;">
                Ingresar respuesta de funcionario
            </button>
        </form>

        <form action="BuscarOficina.jsp" method="post" style="display:inline; margin-left:10px;">
            <input type="hidden" name="cedula" id="cedulaOficinaBuscar">
            <button type="submit" onclick="document.getElementById('cedulaOficinaBuscar').value = document.getElementById('cedulaCaso').value;">
                Buscar
            </button>
        </form>

        <form action="EditarFuncionario.jsp" method="post" style="display:inline; margin-left:10px;">
            <input type="hidden" name="cedula" id="cedulaOficinaEditar">
            <button type="submit" onclick="document.getElementById('cedulaOficinaEditar').value = document.getElementById('cedulaCaso').value;">
                Editar
            </button>
        </form>

        <form action="EliminarFuncionario.jsp" method="post" style="display:inline; margin-left:10px;">
            <input type="hidden" name="cedula" id="cedulaOficinaEliminar">
            <button type="submit" onclick="document.getElementById('cedulaOficinaEliminar').value = document.getElementById('cedulaCaso').value; return confirmarEliminacion();">
                Eliminar
            </button>
        </form>
    </div>

    <script>
        function confirmarEliminacion() {
            return confirm('¿Estás seguro de que deseas eliminar este funcionario?');
        }
    </script>
</div>
</body>
</html>
