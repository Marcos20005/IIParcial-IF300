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

@WebServlet("/EditarFuncionario")
public class EditarFuncionario extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String idEmpleadoBuscado = request.getParameter("cedula"); // asumo que viene por "cedula"

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Editar Funcionario</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");

            if (idEmpleadoBuscado == null || idEmpleadoBuscado.isEmpty()) {
                out.println("<h3>Error: No se proporcionó un ID de empleado válido.</h3>");
            } else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                        String sql = "SELECT Nombre, Lugar, Direccion, Telefono, Cedula, Solucion, CedulaCaso FROM oficinaregional WHERE IDempleado = ?";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setString(1, idEmpleadoBuscado);
                            ResultSet rs = stmt.executeQuery();

                            if (rs.next()) {
                                String nombre = rs.getString("Nombre");
                                String lugar = rs.getString("Lugar");
                                String direccion = rs.getString("Direccion");
                                String telefono = rs.getString("Telefono");
                                String cedula = rs.getString("Cedula");
                                String solucion = rs.getString("Solucion");
                                String cedulaCaso = rs.getString("CedulaCaso");

                                out.println("<h2>Editar Información del Funcionario</h2>");
                                out.println("<form action='ActualizarFuncionario' method='post'>");
                                out.println("<input type='hidden' name='idEmpleado' value='" + idEmpleadoBuscado + "'>");

                                out.println("<label for='nombre'>Nombre:</label>");
                                out.println("<input type='text' id='nombre' name='Nombre' value='" + nombre + "' required><br>");

                                out.println("<label for='lugar'>Lugar:</label>");
                                out.println("<input type='text' id='lugar' name='Lugar' value='" + lugar + "' required><br>");

                                out.println("<label for='direccion'>Dirección:</label>");
                                out.println("<input type='text' id='direccion' name='Direccion' value='" + direccion + "' required><br>");

                                out.println("<label for='telefono'>Teléfono:</label>");
                                out.println("<input type='text' id='telefono' name='Telefono' value='" + telefono + "' required><br>");

                                out.println("<label for='cedula'>Cédula:</label>");
                                out.println("<input type='text' id='cedula' name='Cedula' value='" + cedula + "' required><br>");

                                out.println("<label for='solucion'>Solución:</label>");
                                out.println("<textarea id='solucion' name='Solucion' rows='3'>" + solucion + "</textarea><br>");

                                out.println("<label for='cedulaCaso'>Cédula Caso:</label>");
                                out.println("<input type='text' id='cedulaCaso' name='CedulaCaso' value='" + cedulaCaso + "'><br>");

                                out.println("<div class='botones'>");
                                out.println("<button type='submit'>Guardar Cambios</button>");
                                out.println("</div>");

                                out.println("</form>");
                            } else {
                                out.println("<h3>No se encontró un funcionario con ese ID.</h3>");
                            }
                        }
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    out.println("<p>Error al acceder a la base de datos: " + e.getMessage() + "</p>");
                }
            }

            // Botones para regresar igual que en editar caso
            out.println("<div class='botones'>");
            out.println("<form action='ConsultarOficinas' method='post' style='display:inline;'>");
            out.println("<button type='submit'>Volver a lista de oficinas</button>");
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

