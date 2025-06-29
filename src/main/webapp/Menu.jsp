<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Menú Principal</title>
    <link rel="stylesheet" href="style1.css">
</head>
<body>
    <div class="contenedor">
        <h1>Menú Principal</h1>

        <%--Muestra el nombre del usuario autenticado--%>
        <%
           String usuario = (String) session.getAttribute("usuario");
           if (usuario != null) {
            %>
            <h3>Bienvenido, <%= usuario %></h3>
            <%
           } else {
            response.sendRedirect("Index.jsp");
           }
        %>

        <!-- Contenedor para los botones del menú -->
        <div class="menu-opciones">
            <form action="/miproyectoexamen/ConsultarCasos" method="post">
                <button type="submit" id="caso" class="">
                    <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                        <path d="M3 6h18v2H3V6zm2 4h14v2H5v-2zm-2 4h18v2H3v-2z"/>
                    </svg>
                    Caso
                </button>
            </form>

            <form action="/miproyectoexamen/ConsultarOficinas" method="post">
                <button type="submit" id="oficina">
                    <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                        <path d="M4 22h16v-2H4v2zm14-6h-4v-4h-4v4H6v-6h12v6z"/>
                    </svg>
                    Oficina
                </button>
            </form>

            <form action="/miproyectoexamen/ConsultarUsuarios" method="post">
                <button type="submit" id="usuario">
                    <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                        <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
                    </svg>
                    Usuario
                </button>
            </form>

            <form action="/miproyectoexamen/Index.jsp" method="post">
                <button type="submit" id="salir">
                    <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                        <path d="M16 13v-2H7V8l-5 4 5 4v-3zM20 3H4c-1.1 0-2 .9-2 2v4h2V5h16v14H4v-4H2v4c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z"/>
                    </svg>
                    Salir
                </button>
            </form>
        </div> 

    </div>
</body>
</html>
