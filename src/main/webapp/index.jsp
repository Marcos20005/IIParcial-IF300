<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Proyecto</title>
     <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="contenedor">
        <h1>Autenticación de Usuario</h1>
        <form action="http://localhost:8080/miproyectoexamen/capturarDatos.jsp" method="post">
            <table>
                <tr>
                    <td><label for="nombre">Nombre de usuario:</label></td>
                    <td><input type="text" id="nombre" name="Usuario" required></td>
                </tr>
                <tr>
                    <td><label for="clave">Contraseña:</label></td>
                    <td><input type="text" id="clave" name="Clave" required></td>
                </tr>
            </table>
            <div class="botones">
                <button type="submit"><i class="fi fi-rr-entrance"></i>Ingresar</button>
                <button type="reset"><i class="fi fi-rr-refresh"></i>Limpiar</button>
            </div>
             <%
                String error = (String) request.getAttribute("errorLogin");
                if (error != null) {
            %>
                <p style="color:red;"><%= error %></p>
            <%  }
            %>
        </form>
    </div>
</body>
</html>