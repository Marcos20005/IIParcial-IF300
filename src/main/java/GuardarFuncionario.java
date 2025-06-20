import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//Delaración de servlet para guardar un funcionario en la base de datos.
@WebServlet("/GuardarFuncionario")
public class GuardarFuncionario extends HttpServlet {

    private static final long serialVersionUID = 1L;
    

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "crojas34";


    // Uso del método doPost para manejar la solicitud de guardar un funcionario.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Obtener valores desde el formulario
            String nombre = request.getParameter("nombre");
            String cedulaFuncionario = request.getParameter("cedulaFuncionario");
            String idEmpleado = request.getParameter("idEmpleado");
            String solucion = request.getParameter("solucion");

            String lugar = request.getParameter("lugar");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");

            // Obtener la cédula del caso desde el formulario oculto
            String cedulaCaso = request.getParameter("cedulaCaso");

            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now();

            String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String horaFormateada = horaActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));


            // Se inicia la conexión a la base de datos y se prepara la sentencia SQL para insertar el funcionario.
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement stmt = conn.createStatement()) {

                String sql = "INSERT INTO oficinaregional (IDempleado, Lugar, Direccion, Telefono, Nombre, Cedula, HoraAtencion, FechaAtencion, Solucion, CedulaCaso) VALUES ("
                        + "'" + idEmpleado + "', "
                        + "'" + lugar + "', "
                        + "'" + direccion + "', "
                        + "'" + telefono + "', "
                        + "'" + nombre + "', "
                        + "'" + cedulaFuncionario + "', "
                        + "'" + horaFormateada + "', "
                        + "'" + fechaFormateada + "', "
                        + "'" + solucion + "', "
                        + "'" + cedulaCaso + "')";

                int filasAfectadas = stmt.executeUpdate(sql);

                if (filasAfectadas > 0) {
                    response.sendRedirect("Menu.html");
                } else {
                    out.println("<h3>No se pudo registrar el funcionario.</h3>");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error al guardar funcionario: " + e.getMessage(), e);
        }
    }
}