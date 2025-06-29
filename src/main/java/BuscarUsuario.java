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


// Declaración de servlet para buscar un usuario por login y mostrar su información.
@WebServlet("/BuscarUsuario")
public class BuscarUsuario extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    // Uso del método doPost para manejar la solicitud de búsqueda de un usuario por login.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String loginBuscado = request.getParameter("login");


        // Se inicia la respuesta al usuario.
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Resultado de búsqueda de usuario</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");
            out.println("<h2>Información del usuario</h2>");


            //Se inicia la conexión a la base de datos y se busca el usuario por login.
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    String sql = "SELECT * FROM usuario WHERE login = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, loginBuscado);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
                                out.println("<p><strong>Cédula:</strong> " + rs.getString("cedula") + "</p>");
                                out.println("<p><strong>Nombre 1:</strong> " + rs.getString("nombre1") + "</p>");
                                out.println("<p><strong>Nombre 2:</strong> " + rs.getString("nombre2") + "</p>");
                                out.println("<p><strong>Apellido 1:</strong> " + rs.getString("apellido1") + "</p>");
                                out.println("<p><strong>Apellido 2:</strong> " + rs.getString("apellido2") + "</p>");
                                out.println("<p><strong>Login:</strong> " + rs.getString("login") + "</p>");
                                out.println("<p><strong>Clave:</strong> " + rs.getString("clave") + "</p>");
                            } else {
                                out.println("<p>No se encontró un usuario con ese ID.</p>");
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                out.println("<p>Error en la base de datos: " + e.getMessage() + "</p>");
            } catch (ClassNotFoundException e) {
                throw new ServletException("No se pudo cargar el driver JDBC", e);
            }

            // Botones para volver
            out.println("<div class='botones'>");
            out.println("<form action='ConsultarUsuarios' method='post' style='display:inline;'>");
            out.println("<button type='submit'>Volver a lista de usuarios</button>");
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

