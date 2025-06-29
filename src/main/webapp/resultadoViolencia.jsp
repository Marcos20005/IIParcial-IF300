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
            String color = (exito != null && exito) ? "green" : "red";
        %>
        <p 
             <%= mensaje != null ? mensaje : "No se recibió mensaje del servidor." %>
        </p>
        <a href="Menu.jsp">Volver al menú</a>
    </div>
</body>
</html>
