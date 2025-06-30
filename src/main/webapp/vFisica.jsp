<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%
    String cedula = (String) session.getAttribute("CedulaCaso");
    String tipoViolencia = (String) session.getAttribute("tipoViolencia");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Violencia Fisica</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
    <div class="Violencia-Fisica ventana">
        <h2>Información adicional del caso de Violencia Fisica</h2>
        <form action="AgregarViolencia" method="post">
            <label for="tipoLesion">Ingrese el tipo de lesion sufrida *</label>
            <input type="text" id="tipoLesion" name="tipoLesion" required>

            <label for="atencionMedica">Ingrese la atencion medica recibida *</label>
            <input type="text" id="atencionMedica" name="atencionMedica" required>

            <label for="agresorNombre">Nombre del agresor *</label>
            <input type="text" id="agresorNombre" name="agresorNombre" required>

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
