<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo Usuario</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>

<div class="Nuevo-Usuario ventana" style="display: block;">
    <h2>Información para crear nuevo usuario</h2>

    <form action="/miproyectoexamen/GuardarUsuario.jsp" method="post">

        <label>Cédula *</label>
        <input type="text" name="cedula" required>

        <label>Primer nombre *</label>
        <input type="text" name="nombre1" required>

        <label>Segundo nombre</label>
        <input type="text" name="nombre2">

        <label>Primer apellido *</label>
        <input type="text" name="apellido1" required>

        <label>Segundo apellido</label>
        <input type="text" name="apellido2">

        <label>Nombre de usuario *</label>
        <input type="text" name="login" required>

        <label>Contraseña *</label>
        <input type="password" name="clave" required>

        <div class="botones">
            <button type="submit">💾 Guardar</button>
        </div>

    </form>
</div>

</body>
</html>
