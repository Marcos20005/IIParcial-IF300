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


//Declaración de servlet para eliminar un caso de la base de datos por cédula.
@WebServlet("/EliminarCaso")
public class EliminarCaso extends HttpServlet {

    private static final long serialVersionUID = 1L;


    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";


    // Uso del método doPost para manejar la solicitud de eliminación de un caso.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String cedula = request.getParameter("cedula");


        // Se inicia la respuesta al usuario.
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Eliminar Caso</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");

            if (cedula == null || cedula.trim().isEmpty()) {
                out.println("<h3>Error: No se proporcionó una cédula válida.</h3>");
            } else {
                try {

                    // Buscar el caso con esa cédula y eliminarlo
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                        String sql = "DELETE FROM caso WHERE Cedula = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setString(1, cedula);
                            int filasAfectadas = stmt.executeUpdate();

                            if (filasAfectadas > 0) {
                                out.println("<h3>Caso eliminado exitosamente.</h3>");
                            } else {
                                out.println("<h3>No se encontró ningún caso con esa cédula.</h3>");
                            }
                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    out.println("<p>Error al eliminar el caso: " + e.getMessage() + "</p>");
                }
            }

            out.println("<div class='botones'>");
            out.println("<form action='ConsultarCasos' method='post'>");
            out.println("<button type='submit'>Volver a lista de casos</button>");
            out.println("</form>");

            out.println("<form action='Menu.html' method='get'>");
            out.println("<button type='submit'>Volver al menú</button>");
            out.println("</form>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
