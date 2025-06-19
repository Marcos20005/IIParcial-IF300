import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/GuardarUsuario")
public class GuardarUsuario extends HttpServlet {
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
    String nombre1 = request.getParameter("nombre1");
    String nombre2 = request.getParameter("nombre2");
    String apellido1 = request.getParameter("apellido1");
    String apellido2 = request.getParameter("apellido2");
    String login = request.getParameter("login");
    String clave = request.getParameter("clave");

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO usuario (cedula, nombre1, nombre2, apellido1, apellido2, login, clave) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cedula);
                stmt.setString(2, nombre1);
                stmt.setString(3, nombre2);
                stmt.setString(4, apellido1);
                stmt.setString(5, apellido2);
                stmt.setString(6, login);
                stmt.setString(7, clave);

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    response.sendRedirect("Menu.html");  // redirige después de guardar
                } else {
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<h3>No se pudo registrar el usuario.</h3>");
                    }
                }
            }
        }
    } catch (ClassNotFoundException | SQLException e) {
        try (PrintWriter out = response.getWriter()) {
            out.println("<h3>Ocurrió un error: " + e.getMessage() + "</h3>");
        }
    }
}
}
 