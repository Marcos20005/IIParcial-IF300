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

@WebServlet("/ActualizarUsuario")
public class ActualizarUsuario extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String login = request.getParameter("login"); // ID para buscar
        String cedula = request.getParameter("cedula");
        String nombre1 = request.getParameter("nombre1");
        String nombre2 = request.getParameter("nombre2");
        String apellido1 = request.getParameter("apellido1");
        String apellido2 = request.getParameter("apellido2");
        String clave = request.getParameter("clave");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Actualizar Usuario</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");
            out.println("<h2>Actualizar Información del Usuario</h2>");

            if (login == null || login.isEmpty()) {
                out.println("<p>Error: El login es obligatorio para actualizar el usuario.</p>");
            } else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                        String sql = "UPDATE usuario SET cedula = ?, nombre1 = ?, nombre2 = ?, apellido1 = ?, apellido2 = ?, clave = ? WHERE login = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setString(1, cedula);
                            stmt.setString(2, nombre1);
                            stmt.setString(3, nombre2);
                            stmt.setString(4, apellido1);
                            stmt.setString(5, apellido2);
                            stmt.setString(6, clave);
                            stmt.setString(7, login);

                            int filasActualizadas = stmt.executeUpdate();

                            if (filasActualizadas > 0) {
                                out.println("<p>✅ Usuario actualizado correctamente.</p>");
                            } else {
                                out.println("<p>❌ No se pudo actualizar el usuario. No se encontró el login.</p>");
                            }
                        }
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    out.println("<p>Error al actualizar la base de datos: " + e.getMessage() + "</p>");
                }
            }

            // Botones siempre visibles
            out.println("<div class='botones'>");
            out.println("<form action='ConsultarUsuarios' method='post' style='display:inline;'>");
            out.println("<button type='submit'>Volver a lista de usuarios</button>");
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
