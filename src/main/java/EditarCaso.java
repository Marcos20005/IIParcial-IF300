
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

@WebServlet("/EditarCaso")
public class EditarCaso extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "cRojas34";

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
            out.println("<title>Editar Caso</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");

            if (cedulaBuscada == null || cedulaBuscada.isEmpty()) {
                out.println("<h3>Error: No se proporcionó una cédula válida.</h3>");
            } else {

                // Buscar el caso con esa cédula
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    String sql = "SELECT Nombre, Descripcion FROM caso WHERE Cedula = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, cedulaBuscada);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            String nombre = rs.getString("Nombre");
                            String descripcion = rs.getString("Descripcion");

                            // Mostrar formulario para editar
                            out.println("<h2>Editar Información del Caso</h2>");
                            out.println("<form action='ActualizarCaso' method='post'>");
                            out.println("<input type='hidden' name='cedula' value='" + cedulaBuscada + "'>");
                            out.println("<label for='nombre'>Nombre:</label>");
                            out.println("<input type='text' id='nombre' name='nombre' value='" + nombre + "' required><br>");

                            out.println("<label for='descripcion'>Descripción:</label>");
                            out.println("<textarea id='descripcion' name='descripcion' rows='4'>" + descripcion + "</textarea><br>");

                            out.println("<div class='botones'>");
                            out.println("<button type='submit'>Guardar Cambios</button>");
                            out.println("</div>");
                            out.println("</form>");

                        } else {
                            out.println("<h3>No se encontró un caso con la cédula ingresada.</h3>");
                        }
                    }
                } catch (SQLException e) {
                    out.println("<p>Error al acceder a la base de datos: " + e.getMessage() + "</p>");
                }
            }

            // 🔁 Botones para regresar
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

        } catch (ClassNotFoundException e) {
            throw new ServletException("Error al cargar el driver JDBC", e);
        }
    }
}
