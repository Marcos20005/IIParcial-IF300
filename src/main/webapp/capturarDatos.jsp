<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page session="true" %>
<%
 // Obtener parámetros enviados desde el formulario de login
    String usuario = request.getParameter("Usuario");
    String clave = request.getParameter("Clave");
    String mensajeError = null;

    // Verificar que ambos parámetros existan para procesar el login
    if (usuario != null && clave != null) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1?verifyServerCertificate=false&useSSL=true", "root", "cRojas34");
            stmt = con.createStatement();

             // Consulta para validar usuario y clave
            String query = "SELECT * FROM usuario WHERE login='" + usuario + "' AND clave='" + clave + "'";
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                session.setAttribute("usuario", usuario);
                response.sendRedirect("Menu.jsp");
                return;
            } else {
                mensajeError = "Usuario o clave incorrectos";
            }
        } catch (Exception e) {
            mensajeError = "Ocurrió un error: " + e.getMessage();
        } finally {
            // Cerrar recursos abiertos para evitar fugas
            if (rs != null) try { rs.close(); } catch (Exception e) {}
            if (stmt != null) try { stmt.close(); } catch (Exception e) {}
            if (con != null) try { con.close(); } catch (Exception e) {}
        }
    }
%>

<!-- Si hay error, mostrarlo -->
<%
    if (mensajeError != null) {
%>
    <p style="color:red;"><%= mensajeError %></p>
    <jsp:include page="Index.jsp" />
<%
    }
%>
