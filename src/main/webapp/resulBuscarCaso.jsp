<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado de búsqueda</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Información del caso</h2>

    <%
        String error = (String) request.getAttribute("error");
        Boolean noEncontrado = (Boolean) request.getAttribute("noEncontrado");

        if (error != null) {
    %>
        <p style="color:red;">Error: <%= error %></p>
    <%
        } else if (noEncontrado != null && noEncontrado) {
    %>
        <p>No se encontró un caso con esa cédula.</p>
    <%
        } else {
    %>
        <p><strong>Cédula:</strong> <%= request.getAttribute("cedula") %></p>
        <p><strong>Nombre:</strong> <%= request.getAttribute("nombre") %></p>
        <p><strong>Descripción:</strong> <%= request.getAttribute("descripcion") %></p>
        <p><strong>Fecha:</strong> <%= request.getAttribute("fecha") %></p>
        <p><strong>Número Celular:</strong> <%= request.getAttribute("numeroCelular") %></p>
        <p><strong>Dirección:</strong> <%= request.getAttribute("direccion") %></p>
        <p><strong>Edad:</strong> <%= request.getAttribute("edad") %></p>
        <p><strong>Género:</strong> <%= request.getAttribute("genero") %></p>
        <p><strong>Estado Civil:</strong> <%= request.getAttribute("estadoCivil") %></p>
        <p><strong>Ocupación:</strong> <%= request.getAttribute("ocupacion") %></p>
        <p><strong>Nacionalidad:</strong> <%= request.getAttribute("nacionalidad") %></p>
        <p><strong>Agresor:</strong> <%= request.getAttribute("agresor") %></p>
        <p><strong>Relación con Agresor:</strong> <%= request.getAttribute("relacionAgresor") %></p>
        <p><strong>Género del Agresor:</strong> <%= request.getAttribute("generoAgresor") %></p>
        <p><strong>Tipo de violencia:</strong> <%= request.getAttribute("tipoViolencia") %></p>

        <%
            String tipoViolencia = (String) request.getAttribute("tipoViolencia");
            if ("Violencia Económica".equals(tipoViolencia)) {
        %>
            <p><strong>Tipo Ingreso:</strong> <%= request.getAttribute("tipoIngreso") %></p>
            <p><strong>Cantidad Ingreso:</strong> <%= request.getAttribute("cantidadIngreso") %></p>
        <%
            } else if ("Violencia Física".equals(tipoViolencia)) {
        %>
            <p><strong>Tipo Lesión:</strong> <%= request.getAttribute("tipoLesion") %></p>
            <p><strong>Atención Médica:</strong> <%= request.getAttribute("atencionMedica") %></p>
        <%
            } else if ("Violencia Emocional".equals(tipoViolencia)) {
        %>
            <p><strong>Impacto Psicológico:</strong> <%= request.getAttribute("impactoPsicologico") %></p>
        <%
            } else if ("Violencia Digital".equals(tipoViolencia)) {
        %>
            <p><strong>Plataforma Digital:</strong> <%= request.getAttribute("plataformaDigital") %></p>
        <%
            } else if ("Violencia Sexual".equals(tipoViolencia)) {
        %>
            <p><strong>Tipo de Abuso Sexual:</strong> <%= request.getAttribute("tipoAbusoSexual") %></p>
        <%
            }
        %>

        <h3>Funcionario asignado al caso</h3>
        <%
            String funcNombre = (String) request.getAttribute("funcNombre");
            if (funcNombre != null) {
        %>
            <p><strong>Nombre:</strong> <%= funcNombre %></p>
            <p><strong>Cédula:</strong> <%= request.getAttribute("funcCedula") %></p>
            <p><strong>ID Empleado:</strong> <%= request.getAttribute("funcID") %></p>
            <p><strong>Teléfono:</strong> <%= request.getAttribute("funcTelefono") %></p>
            <p><strong>Dirección:</strong> <%= request.getAttribute("funcDireccion") %></p>
            <p><strong>Lugar:</strong> <%= request.getAttribute("funcLugar") %></p>
            <p><strong>Fecha Atención:</strong> <%= request.getAttribute("funcFechaAtencion") %></p>
            <p><strong>Hora Atención:</strong> <%= request.getAttribute("funcHoraAtencion") %></p>
            <p><strong>Solución:</strong> <%= request.getAttribute("funcSolucion") %></p>
        <%
            } else {
        %>
            <p><em>No hay funcionario asignado a este caso.</em></p>
        <%
            }
        %>

    <%
        }
    %>

    <div class="botones">
        <form action="ConsultarCasos.jsp" method="post">
            <button type="submit">📄 Lista de casos</button>
        </form>
        <form action="Menu.jsp" method="get">
            <button type="submit">🏠 Volver al menú</button>
        </form>
    </div>
</div>
</body>
</html>
