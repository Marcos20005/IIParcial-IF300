
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AgregarViolencia")
public class AgregarInformacionViolencia extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "cRojas34";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Variables Java recibidas en el servlet:
        String acceso = request.getParameter("cedula");
        String tipoViolencia = request.getParameter("tipoViolencia");
        System.out.println(">>> tipoViolencia: " + tipoViolencia);
        String sql = "";
// La SQL concatenada para insertar en la tabla 'casos':
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
            String nombreAgresor = request.getParameter("nombre");
            String relacionAgresor = request.getParameter("relacion");
            String generoAgresor = request.getParameter("genero");
            // Si no es violencia digital, se actualizan los mismos campos pero sin la plataforma
            sql = "UPDATE caso SET "
                    + "TipoIngreso = '" + ingreso + "', "
                    + "Agresor = '" + nombreAgresor + "', "
                    + "RelacionAgresor    = '" + relacionAgresor + "', "
                    + "GeneroAgresor      = '" + generoAgresor + "',"
                    + "CantidadIngreso = '" + impacto + "' "
                    + "WHERE cedula = '" + acceso + "'";
        } else if (tipoViolencia.equals("Violencia Emocional")) {
            String impacto = request.getParameter("impacto");
            String nombreAgresor = request.getParameter("nombre");
            String relacionAgresor = request.getParameter("relacion");
            String generoAgresor = request.getParameter("genero");

            sql = "UPDATE caso SET "
                    + "ImpactoPsicologico = '" + impacto + "', "
                    + "Agresor = '" + nombreAgresor + "', "
                    + "RelacionAgresor = '" + relacionAgresor + "', "
                    + "GeneroAgresor = '" + generoAgresor + "' "
                    + "WHERE cedula = '" + acceso + "'";
        } else if (tipoViolencia.equals("Violencia Física")) {
            String tipoLesion = request.getParameter("tipoLesion");
            String AtencionMedica = request.getParameter("atencionMedica");
            String nombreAgresor = request.getParameter("nombre");
            String relacionAgresor = request.getParameter("relacion");
            String generoAgresor = request.getParameter("genero");

            sql = "UPDATE caso SET "
                    + "TipoLesion = '" + tipoLesion + "', "
                    + "AtencionMedica = '" + AtencionMedica + "', "
                    + "Agresor = '" + nombreAgresor + "', "
                    + "RelacionAgresor = '" + relacionAgresor + "', "
                    + "GeneroAgresor = '" + generoAgresor + "' "
                    + "WHERE cedula = '" + acceso + "'";
        } else if (tipoViolencia.equals("Violencia Sexual")) {
            String TipoAbusoSexual = request.getParameter("abuso");
            String nombreAgresor = request.getParameter("nombre");
            String relacionAgresor = request.getParameter("relacion");
            String generoAgresor = request.getParameter("genero");

            sql = "UPDATE caso SET "
                    + "TipoAbusoSexual = '" + TipoAbusoSexual + "', "
                    + "Agresor = '" + nombreAgresor + "', "
                    + "RelacionAgresor = '" + relacionAgresor + "', "
                    + "GeneroAgresor = '" + generoAgresor + "' "
                    + "WHERE cedula = '" + acceso + "'";
        }
        System.out.println(">>> cedula: " + acceso);

        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                Statement stmt = conn.createStatement();
                int filasAfectadas = stmt.executeUpdate(sql);

                if (filasAfectadas > 0) {
                    out.println("<h3>Información de violencia digital agregada correctamente.</h3>");
                    response.sendRedirect("Menu.html");
                } else {
                    out.println("<h3>No se pudo agregar la información de violencia digital.</h3>");
                }

            } catch (SQLException e) {
                out.println("<h3>Ocurrió un error SQL: " + e.getMessage() + "</h3>");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
