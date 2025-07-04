<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    // Parámetros de conexión a la base de datos
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "cRojas34";

    Connection conn = null;
    PreparedStatement stmt1 = null, stmt2 = null;
    ResultSet rs1 = null, rs2 = null;

    // Variables para almacenar la información recuperada
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

         // Consulta para obtener casos que NO tienen funcionario asignado
      String sqlCasos = "SELECT c.Cedula, c.Nombre FROM caso c LEFT JOIN oficinaregional o ON c.Cedula = o.CedulaCaso WHERE o.CedulaCaso IS NULL";
      stmt2 = conn.prepareStatement(sqlCasos);
      rs2 = stmt2.executeQuery();

      while (rs2.next()) {
          textoCasos.append("Cedula:"+ rs2.getString("Cedula")+",Nombre: "+rs2.getString("Nombre")+"\n");
      }
    } catch (Exception e) {
        textoOficinas.append("Error al consultar: ").append(e.getMessage());
    } finally {
        // Cerrar todos los recursos abiertos para evitar fugas de memoria
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
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Oficinas con respuestas registradas</h2>

    <!-- Área que muestra la lista de funcionarios registrados -->
    <label for="areaOficinas">Funcionarios registrados</label>
    <textarea id="areaOficinas" rows="5" readonly><%= textoOficinas.toString() %></textarea>
    <br><br>

    <!-- Área que muestra la lista de casos sin funcionario asignado -->
    <label for="areaCasos">Cédulas de casos registrados</label>
    <textarea id="areaCasos" rows="5" readonly><%= textoCasos.toString() %></textarea>
    <br><br>

    <!-- Campo para ingresar la cédula del caso y seleccionar la función -->
    <label for="cedulaCaso">Ingrese cédula del caso para asignar funcionario o ID y seleccione función</label><br>
    <input type="text" id="cedulaCaso" name="cedulaCaso"><br><br>

    <div class="botones">
        <!-- Botón para ingresar funcionario -->
        <form action="vFuncionario.jsp" method="post" style="display:inline; margin-left:10px;">
            <input type="hidden" name="cedulaCaso" id="cedulaCasoOculta">
            <button type="submit" onclick="document.getElementById('cedulaCasoOculta').value = document.getElementById('cedulaCaso').value;">
        <i class="fi fi-rr-add-document"></i>     Ingresar respuesta de funcionario
            </button>
        </form>

        <!-- Botones para buscar, editar y eliminar funcionario -->
        <form action="BuscarOficina.jsp" method="post" style="display:inline; margin-left:10px;">
            <input type="hidden" name="cedula" id="cedulaOficinaBuscar">
            <button type="submit" onclick="document.getElementById('cedulaOficinaBuscar').value = document.getElementById('cedulaCaso').value;">
             <i class="fi fi-rr-search"></i>  Buscar
            </button>
        </form>

        <form action="EditarFuncionario.jsp" method="post" style="display:inline; margin-left:10px;">
            <input type="hidden" name="cedula" id="cedulaOficinaEditar">
            <button type="submit" onclick="document.getElementById('cedulaOficinaEditar').value = document.getElementById('cedulaCaso').value;">
           <i class="fi fi-rr-edit"></i>      Editar
            </button>
        </form>

        <form action="EliminarFuncionario.jsp" method="post" style="display:inline; margin-left:10px;">
            <input type="hidden" name="cedula" id="cedulaOficinaEliminar">
            <button type="submit" onclick="document.getElementById('cedulaOficinaEliminar').value = document.getElementById('cedulaCaso').value; return confirmarEliminacion();">
         <i class="fi fi-rr-trash"></i>       Eliminar
            </button>
        </form>

        
        <form action="Menu.jsp" method="post" style="display:inline; margin-left:10px;">
            <button type="submit">
                <i class="fi fi-rr-undo"></i>Volver a Menu
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