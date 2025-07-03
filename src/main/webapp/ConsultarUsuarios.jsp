<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    String URL = "jdbc:mysql://localhost:3306/proyecto1";
    String USER = "root";
    String PASSWORD = "erpalacios";

    StringBuilder textoUsuarios = new StringBuilder();

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT login, Nombre1 FROM usuario";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String login = rs.getString("login");
                    String nombre = rs.getString("Nombre1");
                    textoUsuarios.append("Login: ").append(login).append(", Nombre: ").append(nombre).append("\n");
                }
            }
        } catch (SQLException e) {
            textoUsuarios.append("Error al consultar los usuarios: ").append(e.getMessage());
        }
    } catch (ClassNotFoundException e) {
        textoUsuarios.append("Error al cargar el driver JDBC.");
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Usuarios Registrados</title>
    <link rel="stylesheet" href="estilo.css">
</head>
<body>
<div class="ventana">
    <h2>Usuarios Registrados</h2>

    <label for="areaUsuarios">Lista de usuarios</label>
    <textarea id="areaUsuarios" rows="5" readonly><%= textoUsuarios.toString() %></textarea>

    <label for="cedula">Ingrese ID del usuario</label>
    <input type="text" id="cedula" name="cedula">

    <div class="botones">
        <!-- Botón Ingresar nuevo usuario -->
        <form action="vUsuario.jsp" method="post" style="display:inline;">
            <button type="submit">Ingresar nuevo usuario</button>
        </form>

        <!-- Botón Buscar usuario -->
        <form action="BuscarUsuario.jsp" method="post" style="display:inline;">
            <input type="hidden" name="login" id="loginOculto">
            <button type="submit" onclick="document.getElementById('loginOculto').value = document.getElementById('cedula').value;">
                Buscar
            </button>
        </form>

        <!-- Botón Editar usuario -->
        <form action="EditarUsuario.jsp" method="post" style="display:inline;">
            <input type="hidden" name="login" id="loginEditarOculto">
            <button type="submit" onclick="document.getElementById('loginEditarOculto').value = document.getElementById('cedula').value;">
                Editar
            </button>
        </form>

        <!-- Botón Eliminar usuario -->
        <form action="EliminarUsuario.jsp" method="post" style="display:inline;">
            <input type="hidden" name="login" id="loginEliminarOculto">
            <button type="submit" onclick="document.getElementById('loginEliminarOculto').value = document.getElementById('cedula').value; return confirmarEliminacion();">
                Eliminar
            </button>
        </form>
         <form action="Menu.jsp" method="post">
                <button type="submit">Volver a Menu </button>
            </form>
    </div>

    <script>
        function confirmarEliminacion() {
            return confirm('¿Estás seguro de que desea eliminar este usuario?');
        }
    </script>
</div>
</body>
</html>
