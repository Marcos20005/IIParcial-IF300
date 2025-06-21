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


//Declaración de servlet para guardar un nuevo usuario en la base de datos.
@WebServlet("/GuardarUsuario")
public class GuardarUsuario extends HttpServlet {

    private static final long serialVersionUID = 1L;


    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";


    // Uso del método doPost para manejar la solicitud de guardar un usuario.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        System.out.println(">>> GuardarUsuario doPost method called.");
        String cedula = request.getParameter("cedula");
        String nombre1 = request.getParameter("nombre");
        String nombre2 = request.getParameter("nombre2");
        String apellido1 = request.getParameter("apellido");
        String apellido2 = request.getParameter("apellido2");
        String login = request.getParameter("login");
        String clave = request.getParameter("clave");


        // Se inicia la conexion a la base de datos y se prepara la sentencia SQL para insertar el usuario.
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
                    System.out.println("Aniadido con exito: " + filasAfectadas);
                    if (filasAfectadas > 0) {
                        System.out.println(">>> Usuario guardado exitosamente.");
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
