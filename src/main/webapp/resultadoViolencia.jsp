<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado Violencia</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
    <div class="ventana">
        <h2>Resultado</h2>

        <%
            Boolean exito = (Boolean) request.getAttribute("exito");
            String mensaje = (String) request.getAttribute("mensaje");
            String claseMensaje = (exito != null && exito) ? "mensaje-exito" : "mensaje-error";
        %>

        <p class="<%= claseMensaje %>">
            <%= (mensaje != null) ? mensaje : "No se recibió mensaje del servidor." %>
        </p>

        <div class="botones">
            <a href="ConsultarCasos"><button>🔙 Volver a la pestaña anterior</button></a>
            <a href="Menu.jsp"><button>🏠 Ir al menú</button></a>
        </div>
    </div>
</body>
</html>

