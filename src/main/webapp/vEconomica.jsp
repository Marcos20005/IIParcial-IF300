<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%
    String cedula = (String) session.getAttribute("CedulaCaso");
    String tipoViolencia = (String) session.getAttribute("tipoViolencia");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Violencia Economica</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
    <div class="Violencia-Economica ventana">
        <h2>Información adicional del caso de Violencia Economica</h2>
        <form action="AgregarViolencia" method="post">
            <label for="ingreso">Ingrese el tipo de ingreso afectado *</label>
            <input type="text" id="ingreso" name="ingreso" required>"

            <label for="agresorNombre">Nombre del agresor *</label>
            <input type="text" id="agresorNombre" name="agresorNombre" required>

            <label for="impacto">Ingrese el impacto económico que tuvo la persona *</label>"
            <input type="text" id="impacto" name="impacto" required>"

            <label for="relacionAgresor">Relación con el agresor *</label>
            <input type="text" id="relacionAgresor" name="relacionAgresor" required>

            <label>Género del agresor *</label>
            <div class="radio-grupo">
                <label><input type="radio" name="agresorGenero" value="Femenino" required> Femenino</label>
                <label><input type="radio" name="agresorGenero" value="Masculino" required> Masculino</label>
                <label><input type="radio" name="agresorGenero" value="Otro" required> Otro</label>
            </div>

            <input type="hidden" name="cedula" value="<%= cedula != null ? cedula : "" %>">
            <input type="hidden" name="tipoViolencia" value="<%= tipoViolencia != null ? tipoViolencia : "" %>">

            <div class="botones">
                <button type="submit">💾 Guardar</button>
            </div>
        </form>
    </div>
</body>
</html>
