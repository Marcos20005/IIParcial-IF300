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


//Declaración de servlet para eliminar un usuario de la base de datos.
@WebServlet("/EliminarUsuario")
public class EliminarUsuario extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";


    // Uso del método doPost para manejar la solicitud de eliminación de un usuario.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String login = request.getParameter("login");


        // Se inicia la respuesta al usuario.
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Eliminar Usuario</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");

            if (login == null || login.trim().isEmpty()) {
                out.println("<h3>Error: No se proporcionó un ID de usuario válido.</h3>");
            } else {
                try {

                    // Buscar el usuario con ese ID y eliminarlo, inicio de conexión a la base de datos.
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                        String sql = "DELETE FROM usuario WHERE login = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setString(1, login);
                            int filasAfectadas = stmt.executeUpdate();

                            if (filasAfectadas > 0) {
                                out.println("<h3>Usuario eliminado exitosamente.</h3>");
                            } else {
                                out.println("<h3>No se encontró ningún usuario con ese ID.</h3>");
                            }
                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    out.println("<p>Error al eliminar el usuario: " + e.getMessage() + "</p>");
                }
            }

            out.println("<div class='botones'>");
            out.println("<form action='ConsultarUsuarios' method='post'>");
            out.println("<button type='submit'>Volver a lista de usuarios</button>");
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
