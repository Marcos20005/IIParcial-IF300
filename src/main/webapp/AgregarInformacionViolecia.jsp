<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");

    // Obtener la cédula y tipo de violencia del formulario
    String acceso = request.getParameter("cedula");
    String tipoViolencia = request.getParameter("tipoViolencia");
    String sql = null;

    // Construcción dinámica de la consulta SQL según el tipo de violencia
    if (tipoViolencia != null) {
        switch (tipoViolencia) {
            case "Violencia Digital":
            // Parámetros específicos para violencia digital
                String plataformaAgresion = request.getParameter("plataformaAgresion");
                String agresorNombreD = request.getParameter("agresorNombre");
                String relacionAgresorD = request.getParameter("relacionAgresor");
                String agresorGeneroD = request.getParameter("agresorGenero");
                sql = "UPDATE caso SET PlataformaDigital = '" + plataformaAgresion + "', Agresor = '" + agresorNombreD + "', RelacionAgresor = '" + relacionAgresorD + "', GeneroAgresor = '" + agresorGeneroD + "' WHERE cedula = '" + acceso + "'";
                break;
            case "Violencia Económica":
            // Parámetros específicos para violencia económica
                String ingreso = request.getParameter("ingreso");
                String impactoE = request.getParameter("impacto");
                String agresorNombreE = request.getParameter("agresorNombre");
                String relacionAgresorE = request.getParameter("relacionAgresor");
                String generoAgresorE = request.getParameter("agresorGenero");
                sql = "UPDATE caso SET TipoIngreso = '" + ingreso + "', Agresor = '" + agresorNombreE + "', RelacionAgresor = '" + relacionAgresorE + "', GeneroAgresor = '" + generoAgresorE + "', CantidadIngreso = '" + impactoE + "' WHERE cedula = '" + acceso + "'";
                break;
            case "Violencia Emocional":
            // Parámetros específicos para violencia emocional
                String impacto = request.getParameter("impacto");
                String agresorNombreEm = request.getParameter("agresorNombre");
                String relacionAgresorEm = request.getParameter("relacionAgresor");
                String generoAgresorEm = request.getParameter("agresorGenero");
                sql = "UPDATE caso SET ImpactoPsicologico = '" + impacto + "', Agresor = '" + agresorNombreEm + "', RelacionAgresor = '" + relacionAgresorEm + "', GeneroAgresor = '" + generoAgresorEm + "' WHERE cedula = '" + acceso + "'";
                break;
            case "Violencia Física":
            // Parámetros específicos para violencia física
                String tipoLesion = request.getParameter("tipoLesion");
                String atencionMedica = request.getParameter("atencionMedica");
                String agresorNombreF = request.getParameter("agresorNombre");
                String relacionAgresorF = request.getParameter("relacionAgresor");
                String generoAgresorF = request.getParameter("agresorGenero");
                sql = "UPDATE caso SET TipoLesion = '" + tipoLesion + "', AtencionMedica = '" + atencionMedica + "', Agresor = '" + agresorNombreF + "', RelacionAgresor = '" + relacionAgresorF + "', GeneroAgresor = '" + generoAgresorF + "' WHERE cedula = '" + acceso + "'";
                break;
            case "Violencia Sexual":
            // Parámetros específicos para violencia sexual
                String tipoAbusoSexual = request.getParameter("abuso");
                String agresorNombreS = request.getParameter("agresorNombre");
                String relacionAgresorS = request.getParameter("relacionAgresor");
                String generoAgresorS = request.getParameter("agresorGenero");
                sql = "UPDATE caso SET TipoAbusoSexual = '" + tipoAbusoSexual + "', Agresor = '" + agresorNombreS + "', RelacionAgresor = '" + relacionAgresorS + "', GeneroAgresor = '" + generoAgresorS + "' WHERE cedula = '" + acceso + "'";
                break;
        }
    }

    boolean exito = false;
    String mensaje = "";

    if (sql != null) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1", "root", "cRojas34")) {
                try (Statement stmt = conn.createStatement()) {
                    int filas = stmt.executeUpdate(sql);
                    if (filas > 0) {
                        mensaje = "✅ ¡Información adicional guardada correctamente!";
                        exito = true;
                    } else {
                        mensaje = "⚠️ No se pudo guardar la información adicional.";
                    }
                }
            }
        } catch (Exception e) {
            mensaje = "❌ Error: " + e.getMessage();
        }
    } else {
        mensaje = "❌ Tipo de violencia no reconocido o datos incompletos.";
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado de Registro</title>
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Resultado</h2>
    <p><%= mensaje %></p>
    <div class="botones">
        <form action="Menu.jsp" method="get">
            <button type="submit"><i class="fi fi-rr-undo"></i> Volver al Menú</button>
        </form>
    </div>
</div>
</body>
</html>
