import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BuscarCaso")
public class BuscarCaso extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String cedulaBuscada = request.getParameter("cedula");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Resultado de búsqueda</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");
            out.println("<h2>Información del caso</h2>");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    String sql = "SELECT * FROM caso WHERE Cedula = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, cedulaBuscada);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
                                out.println("<p><strong>Cédula:</strong> " + rs.getString("Cedula") + "</p>");
                                out.println("<p><strong>Nombre:</strong> " + rs.getString("Nombre") + "</p>");
                                out.println("<p><strong>Descripción:</strong> " + rs.getString("Descripcion") + "</p>");
                                out.println("<p><strong>Fecha:</strong> " + rs.getString("Fecha") + "</p>");
                                out.println("<p><strong>NumeroCelular:</strong> " + rs.getString("NumeroCelular") + "</p>");
                                out.println("<p><strong>Direccion:</strong> " + rs.getString("Direccion") + "</p>");
                                out.println("<p><strong>Edad:</strong> " + rs.getString("Edad") + "</p>");
                                out.println("<p><strong>Genero:</strong> " + rs.getString("Genero") + "</p>");
                                out.println("<p><strong>EstadoCivil:</strong> " + rs.getString("EstadoCivil") + "</p>");
                                out.println("<p><strong>Ocupacion:</strong> " + rs.getString("Ocupacion") + "</p>");
                                out.println("<p><strong>Nacionalidad:</strong> " + rs.getString("Nacionalidad") + "</p>");
                                out.println("<p><strong>Agresor:</strong> " + rs.getString("Agresor") + "</p>");
                                out.println("<p><strong>Relacion con Agresor:</strong> " + rs.getString("RelacionAgresor") + "</p>");
                                out.println("<p><strong>Genero de Agresor:</strong> " + rs.getString("GeneroAgresor") + "</p>");
                                String tipoViolencia = rs.getString("TipoViolencia");

                                out.println("<p><strong>Tipo de violencia:</strong> " + tipoViolencia + "</p>");

                                switch (tipoViolencia) {
                                    case "Violencia Económica":
                                        out.println("<p><strong>TipoIngreso:</strong> " + rs.getString("TipoIngreso") + "</p>");
                                        out.println("<p><strong>CantidadIngreso:</strong> " + rs.getString("CantidadIngreso") + "</p>");
                                        break;
                                    case "Violencia Física":
                                        out.println("<p><strong>TipoLesion:</strong> " + rs.getString("TipoLesion") + "</p>");
                                        out.println("<p><strong>AtencionMedica:</strong> " + rs.getString("AtencionMedica") + "</p>");
                                        break;
                                    case "Violencia Emocional":
                                        out.println("<p><strong>ImpactoPsicologico:</strong> " + rs.getString("ImpactoPsicologico") + "</p>");
                                        break;
                                    case "Violencia Digital":
                                        out.println("<p><strong>PlataformaDigital:</strong> " + rs.getString("PlataformaDigital") + "</p>");
                                        break;
                                    case "Violencia Sexual":
                                        out.println("<p><strong>TipoAbusoSexual:</strong> " + rs.getString("TipoAbusoSexual") + "</p>");
                                        break;
                                    default:
                                        out.println("<p><strong>Información adicional:</strong> No registrada</p>");
                                }
                            } else {
                                out.println("<p>No se encontró un caso con esa cédula.</p>");
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                out.println("<p>Error en la base de datos: " + e.getMessage() + "</p>");
            } catch (ClassNotFoundException e) {
                throw new ServletException("No se pudo cargar el driver JDBC", e);
            }

            // Botones
            out.println("<div class='botones'>");
            out.println("<form action='ConsultarCasos' method='post' style='display:inline;'>");
            out.println("<button type='submit'>Volver a lista de casos</button>");
            out.println("</form>");

            out.println("<form action='Menu.html' method='get' style='display:inline;'>");
            out.println("<button type='submit'>Volver al menú</button>");
            out.println("</form>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        }
    }
}
