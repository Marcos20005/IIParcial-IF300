
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

@WebServlet("/GuardarFuncionario")
public class GuardarFuncionario extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "cRojas34";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
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

            // Obtener fecha y hora actual del sistema
            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now();

            String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String horaFormateada = horaActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Establecer conexión con la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();

            // Consulta INSERT para guardar los datos
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
                out.println("<h3>Funcionario registrado exitosamente.</h3>");
                response.sendRedirect("Menu.html");
            } else {
                out.println("<h3>No se pudo registrar el funcionario.</h3>");
            }

            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            out.println("<h3>Ocurrió un error: " + e.getMessage() + "</h3>");
        }
    }
}
