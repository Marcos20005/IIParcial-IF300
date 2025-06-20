
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

@WebServlet("/ConsultarUsuarios")
public class ConsultarUsuarios extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
            out.println("<title>Usuarios Registrados</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");
            out.println("<h2>Usuarios Registrados</h2>");

            out.println("<label for='areaUsuarios'>Lista de usuarios</label>");
            out.println("<textarea id='areaUsuarios' rows='5' readonly>");

            // Conexión y consulta
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT login, Nombre1  FROM usuario";
                try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        String idUsuario = rs.getString("login");
                        String nombre = rs.getString("Nombre1");

                        out.println("Login: " + idUsuario + ", Nombre: " + nombre);
                        out.println(); // salto de línea
                    }
                }
            } catch (SQLException e) {
                out.println("Error al consultar los usuarios: " + e.getMessage());
            }

            out.println("</textarea>");

            out.println("<label for='cedula'>Ingrese ID del usuario</label>");
            out.println("<input type='text' id='cedula' name='cedula'>");

            out.println("<div class='botones'>");
            out.println("<form action=\"vUsuario\" method=\"post\" style=\"display:inline;\">");
            out.println("<button type=\"submit\">Ingresar nuevo usuario</button>");
            out.println("</form>");

            out.println("<button>Buscar</button>");
            out.println("<button>Editar</button>");
            out.println("<button>Eliminar</button>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException e) {
            throw new ServletException("Error al cargar el driver JDBC", e);
        }
    }
}
