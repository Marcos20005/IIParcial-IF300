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


//Inicialiazación de servlet para editar la información de un usuario en la base de datos.
@WebServlet("/EditarUsuario")
public class EditarUsuario extends HttpServlet {

    private static final long serialVersionUID = 1L;


    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "cRojas34";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String loginBuscado = request.getParameter("login");


        // Se inicia la respuesta al usuario.
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Editar Usuario</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");

            if (loginBuscado == null || loginBuscado.isEmpty()) {
                out.println("<h3>Error: No se proporcionó un ID válido.</h3>");
            } else {

                // Buscar el usuario con ese login y se empieza lo necesario para ejecutar la consulta.
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                        String sql = "SELECT * FROM usuario WHERE login = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setString(1, loginBuscado);
                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next()) {
                                    // Mostrar formulario con los datos actuales
                                    out.println("<h2>Editar Información del Usuario</h2>");
                                    out.println("<form action='ActualizarUsuario' method='post'>");

                                    // Enviamos el login como hidden para la identificación
                                    out.println("<input type='hidden' name='login' value='" + rs.getString("login") + "'>");

                                    out.println("<label for='cedula'>Cédula:</label>");
                                    out.println("<input type='text' id='cedula' name='cedula' value='" + rs.getString("cedula") + "' required><br>");

                                    out.println("<label for='nombre1'>Nombre 1:</label>");
                                    out.println("<input type='text' id='nombre1' name='nombre1' value='" + rs.getString("nombre1") + "' required><br>");

                                    out.println("<label for='nombre2'>Nombre 2:</label>");
                                    out.println("<input type='text' id='nombre2' name='nombre2' value='" + rs.getString("nombre2") + "'><br>");

                                    out.println("<label for='apellido1'>Apellido 1:</label>");
                                    out.println("<input type='text' id='apellido1' name='apellido1' value='" + rs.getString("apellido1") + "' required><br>");

                                    out.println("<label for='apellido2'>Apellido 2:</label>");
                                    out.println("<input type='text' id='apellido2' name='apellido2' value='" + rs.getString("apellido2") + "'><br>");

                                    out.println("<label for='clave'>Clave:</label>");
                                    out.println("<input type='password' id='clave' name='clave' value='" + rs.getString("clave") + "' required><br>");

                                    out.println("<div class='botones'>");
                                    out.println("<button type='submit'>Guardar Cambios</button>");
                                    out.println("</div>");
                                    out.println("</form>");
                                } else {
                                    out.println("<h3>No se encontró un usuario con ese ID.</h3>");
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    out.println("<p>Error al acceder a la base de datos: " + e.getMessage() + "</p>");
                } catch (ClassNotFoundException e) {
                    throw new ServletException("Error al cargar el driver JDBC", e);
                }
            }

            // Botones para regresar
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
