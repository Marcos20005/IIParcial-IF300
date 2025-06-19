
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

@WebServlet("/ConsultarCasos")
public class ConsultarCasos extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Define your DB credentials here
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "cRojas34";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Registros de casos</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");
            out.println("<h2>Registros de casos</h2>");

            out.println("<label for='areaCasos'>Casos registrados</label>");
            out.println("<textarea id='areaCasos' rows='5' readonly>");

            // Consultar base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT Cedula, Nombre FROM caso";
                try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        String cedula = rs.getString("Cedula");
                        String nombre = rs.getString("Nombre");
                        out.println("Cédula: " + cedula + ", Nombre: " + nombre);
                    }
                }
            } catch (SQLException e) {
                out.println("Error al consultar los casos: " + e.getMessage());
            }

            out.println("</textarea>");

            out.println("<label for='cedula'>Ingrese número de cédula de caso</label>");
            out.println("<input type='text' id='cedula' name='cedula'>");

            out.println("<div class='botones'>");
            out.println("<button onclick=\"window.location.href='AgregarCaso.html'\">Ingresar nuevo Caso</button>");
            out.println("<form action='BuscarCaso' method='post'>");
            out.println("<input type='hidden' name='cedula' id='cedulaOculta'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaOculta').value = document.getElementById('cedula').value;\">Buscar</button>");
            out.println("</form>");

            out.println("<form action='EditarCaso' method='post'>");
            out.println("<input type='hidden' name='cedula' id='cedulaEditarOculta'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaEditarOculta').value = document.getElementById('cedula').value;\">Editar</button>");
            out.println("</form>");

            out.println("<form action='EliminarCaso' method='post'>");
            out.println("<input type='hidden' name='cedula' id='cedulaEliminarOculta'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaEliminarOculta').value = document.getElementById('cedula').value;\">Eliminar</button>");
            out.println("</form>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException e) {
            throw new ServletException("Error al cargar el driver JDBC", e);
        }
    }
}
