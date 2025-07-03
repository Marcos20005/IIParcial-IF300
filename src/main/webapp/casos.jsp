<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registros de casos</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Registros de casos</h2>

    <label for="areaCasos">Casos registrados</label>
    <textarea id="areaCasos" rows="5" readonly><%
        // Obtener la lista de casos  
        List<String> listaCasos = (List<String>) request.getAttribute("listaCasos");
        if (listaCasos != null) {
            // Mostrar cada caso en una línea dentro del textarea
            for (String caso : listaCasos) {
                out.println(caso);
            }
        }
    %></textarea>

    <label for="cedula">Ingrese el número de cédula de caso</label>
    <input type="text" id="cedula" name="cedula">

    <div class="botones">
        <button onclick="window.location.href='AgregarCaso.jsp'">Ingresar nuevo Caso</button>

        <form action="BuscarCaso" method="post" style="display:inline;">
            <input type="hidden" name="cedula" id="cedulaOcultaBuscar">
            <button type="submit" onclick="document.getElementById('cedulaOcultaBuscar').value = document.getElementById('cedula').value;">Buscar</button>
        </form>

        <form action="EditarCaso" method="post" style="display:inline;">
            <input type="hidden" name="cedula" id="cedulaOcultaEditar">
            <button type="submit" onclick="document.getElementById('cedulaOcultaEditar').value = document.getElementById('cedula').value;">Editar</button>
        </form>

        <form action="EliminarCaso" method="post" style="display:inline;">
            <input type="hidden" name="cedula" id="cedulaOcultaEliminar">
            <button type="submit" onclick="document.getElementById('cedulaOcultaEliminar').value = document.getElementById('cedula').value; return confirmarEliminarCaso();">Eliminar</button>
        </form>
    </div>

</div>

<script>
    // Función que pregunta confirmación antes de eliminar un caso
    function confirmarEliminarCaso() {
        return confirm('¿Está seguro de que desea eliminar este caso?');
    }
</script>

</body>
</html>
