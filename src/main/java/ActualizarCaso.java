import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ActualizarCaso")
public class ActualizarCaso extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Actualizar Caso</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");
            out.println("<h2>Actualizar Información del Caso</h2>");

            if (cedula == null || cedula.isEmpty()) {
                out.println("<p>Error: La cédula es obligatoria para actualizar el caso.</p>");
            } else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                        String sql = "UPDATE caso SET Nombre = ?, Descripcion = ? WHERE Cedula = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setString(1, nombre);
                            stmt.setString(2, descripcion);
                            stmt.setString(3, cedula);

                            int filasActualizadas = stmt.executeUpdate();

                            if (filasActualizadas > 0) {
                                out.println("<p>✅ Caso actualizado correctamente.</p>");
                            } else {
                                out.println("<p>❌ No se pudo actualizar el caso. No se encontró la cédula.</p>");
                            }
                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    out.println("<p>Error al actualizar la base de datos: " + e.getMessage() + "</p>");
                }
            }

            // Botones siempre visibles
            out.println("<div class='botones'>");
            out.println("<form action='ConsultarCasos' method='post' style='display:inline;'>");
            out.println("<button type='submit'>Volver a lista de casos</button>");
            out.println("</form>");

            out.println("<form action='Menu.html' method='get' style='display:inline; margin-left:10px;'>");
            out.println("<button type='submit'>Volver al menú</button>");
            out.println("</form>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
