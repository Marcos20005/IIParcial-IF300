<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String cedulaCaso = request.getParameter("cedulaCaso");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agregar Funcionario</title>
    <link rel="stylesheet" href="https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css">
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="formulario-detalle ventana">
    <h2>Registre la información del funcionario</h2>
    <form action="GuardarFuncionario.jsp" method="post">
        <!-- Info funcionario -->
        <label>Nombre del funcionario *</label>
        <input type="text" name="nombre" required>
        
        <label>Cédula del funcionario *</label>
        <input type="text" name="cedulaFuncionario" required>
        
        <label>Código del funcionario *</label>
        <input type="text" name="idEmpleado" required>
        
        <label>Solución propuesta *</label>
        <textarea name="solucion" required></textarea>
        
        <!-- Info oficina -->
        <label>Lugar de oficina *</label>
        <input type="text" name="lugar" required>
        
        <label>Teléfono oficina *</label>
        <input type="text" name="telefono" required>
        
        <label>Dirección oficina *</label>
        <input type="text" name="direccion" required>
        
        <!-- Cédula del caso oculta -->
        <input type="hidden" name="cedulaCaso" value="<%= cedulaCaso %>">
        
        <div class="botones">
            <button type="submit"><i class="fi fi-rr-disk"></i> Guardar</button>
        </div>
    </form>
</div>
</body>
</html>
