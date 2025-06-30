    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;
    import java.sql.Statement;

    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;


    // Declaración de servlet para agregar la informacion de cada tipo de violencia.
    @WebServlet("/AgregarViolencia")
    public class AgregarInformacionViolencia extends HttpServlet {

        private static final long serialVersionUID = 1L;


        // Datos de conexión a la base de datos
        private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
        private static final String USER = "root";
        private static final String PASSWORD = "erpalacios";


        // Uso del método doPost para manejar la solicitud de agregar información de violencia.
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            request.setCharacterEncoding("UTF-8");

            // Variables Java recibidas en el servlet:
            String acceso = request.getParameter("cedula");
            String tipoViolencia = request.getParameter("tipoViolencia");
            String sql = "";

    // Otencion de los parametros según el tipo de violencia seleccionado y se actualiza la fila dependiendo del tipo de violencia.
            if (tipoViolencia.equals("Violencia Digital")) {
                String plataformaAgresion = request.getParameter("plataformaAgresion");
                String agresorNombre = request.getParameter("agresorNombre");
                String relacionAgresor = request.getParameter("relacionAgresor");
                String agresorGenero = request.getParameter("agresorGenero");
                // Si es violencia digital, se actualiza la plataforma y datos del agresor
                sql = "UPDATE caso SET "
                        + "PlataformaDigital = '" + plataformaAgresion + "', "
                        + "Agresor = '" + agresorNombre + "', "
                        + "RelacionAgresor    = '" + relacionAgresor + "', "
                        + "GeneroAgresor      = '" + agresorGenero + "' "
                        + "WHERE cedula = '" + acceso + "'";
            } else if (tipoViolencia.equals("Violencia Económica")) {
                String ingreso = request.getParameter("ingreso");
                String impacto = request.getParameter("impacto");
                String agresorNombre = request.getParameter("agresorNombre");
                String relacionAgresor = request.getParameter("relacionAgresor");
                String generoAgresor = request.getParameter("agresorGenero");
                // Si no es violencia digital, se actualizan los mismos campos pero sin la plataforma
                sql = "UPDATE caso SET "
                        + "TipoIngreso = '" + ingreso + "', "
                        + "Agresor = '" + agresorNombre + "', "
                        + "RelacionAgresor    = '" + relacionAgresor + "', "
                        + "GeneroAgresor      = '" + generoAgresor + "',"
                        + "CantidadIngreso = '" + impacto + "' "
                        + "WHERE cedula = '" + acceso + "'";
            } else if (tipoViolencia.equals("Violencia Emocional")) {
                String impacto = request.getParameter("impacto");
                String agresorNombre = request.getParameter("agresorNombre");
                String relacionAgresor = request.getParameter("relacionAgresor");
                String generoAgresor = request.getParameter("agresorGenero");

                sql = "UPDATE caso SET "
                        + "ImpactoPsicologico = '" + impacto + "', "
                        + "Agresor = '" + agresorNombre + "', "
                        + "RelacionAgresor = '" + relacionAgresor + "', "
                        + "GeneroAgresor = '" + generoAgresor + "' "
                        + "WHERE cedula = '" + acceso + "'";
            } else if (tipoViolencia.equals("Violencia Física")) {
                String tipoLesion = request.getParameter("tipoLesion");
                String AtencionMedica = request.getParameter("atencionMedica");
                String agresorNombre = request.getParameter("agresorNombre");
                String relacionAgresor = request.getParameter("relacionAgresor");
                String generoAgresor = request.getParameter("agresorGenero");

                sql = "UPDATE caso SET "
                        + "TipoLesion = '" + tipoLesion + "', "
                        + "AtencionMedica = '" + AtencionMedica + "', "
                        + "Agresor = '" + agresorNombre + "', "
                        + "RelacionAgresor = '" + relacionAgresor + "', "
                        + "GeneroAgresor = '" + generoAgresor + "' "
                        + "WHERE cedula = '" + acceso + "'";
            } else if (tipoViolencia.equals("Violencia Sexual")) {
                String TipoAbusoSexual = request.getParameter("abuso");
                String agresorNombre = request.getParameter("agresorNombre");
                String relacionAgresor = request.getParameter("relacionAgresor");
                String generoAgresor = request.getParameter("agresorGenero");

                sql = "UPDATE caso SET "
                        + "TipoAbusoSexual = '" + TipoAbusoSexual + "', "
                        + "Agresor = '" + agresorNombre + "', "
                        + "RelacionAgresor = '" + relacionAgresor + "', "
                        + "GeneroAgresor = '" + generoAgresor + "' "
                        + "WHERE cedula = '" + acceso + "'";
            }
            // Se inicia la respuesta al usuario.
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                Statement stmt = conn.createStatement();
                int filasAfectadas = stmt.executeUpdate(sql);

                if (filasAfectadas > 0) {
                    request.setAttribute("mensaje", "¡Información adicional guardada correctamente!");
                    request.setAttribute("exito", true);
                } else {
                    request.setAttribute("mensaje", "No se pudo guardar la información adicional.");
                    request.setAttribute("exito", false);
                }

            } catch (SQLException e) {
                request.setAttribute("mensaje", "Error SQL: " + e.getMessage());
                request.setAttribute("exito", false);
            }

        } catch (ClassNotFoundException e) {
            request.setAttribute("mensaje", "Error al cargar el driver JDBC.");
            request.setAttribute("exito", false);
        }

        request.getRequestDispatcher("resultadoViolencia.jsp").forward(request, response);
    }
}