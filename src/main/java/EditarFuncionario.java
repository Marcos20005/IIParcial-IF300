
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Declaración de servlet para editar la información de un funcionario en la base de datos.
@WebServlet("/EditarFuncionario")
public class EditarFuncionario extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    // Uso del método doPost para manejar la solicitud de edición de un funcionario.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtención del ID del funcionario desde la solicitud.
        String idFuncionario = request.getParameter("cedula");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Se inicia la respuesta al usuario.
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'><head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Editar Funcionario</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head><body>");
            out.println("<div class='ventana'>");
            out.println("<h2>Editar Funcionario</h2>");

            // Se inicia la conexión a la base de datos y se busca el funcionario por ID.
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

                String sql = "SELECT * FROM oficinaregional WHERE IDempleado = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, idFuncionario);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    out.println("<form action='ActualizarFuncionario' method='post'>");

                    out.printf("<input type='hidden' name='idEmpleado' value='%s'>%n", rs.getString("IDempleado"));

                    out.println("<label>Nombre:</label>");
                    out.printf("<input type='text' name='nombre' value='%s' required><br>", rs.getString("Nombre"));

                    out.println("<label>Cédula:</label>");
                    out.printf("<input type='text' name='cedulaFuncionario' value='%s' required><br>", rs.getString("Cedula"));

                    out.println("<label>Teléfono:</label>");
                    out.printf("<input type='text' name='telefono' value='%s' required><br>", rs.getString("Telefono"));

                    out.println("<label>Dirección:</label>");
                    out.printf("<input type='text' name='direccion' value='%s' required><br>", rs.getString("Direccion"));

                    out.println("<label>Lugar:</label>");
                    out.printf("<input type='text' name='lugar' value='%s' required><br>", rs.getString("Lugar"));

                    out.println("<label>Solución propuesta:</label>");
                    out.printf("<textarea name='solucion' required>%s</textarea><br>", rs.getString("Solucion"));

                    out.printf("<input type='hidden' name='cedulaCaso' value='%s'>%n", rs.getString("CedulaCaso"));

                    // Comienza bloque de botones con estilo
                    out.println("<div class='botones'>");

                    out.println("<button type='submit'>Guardar cambios</button>");
                    out.println("</form>"); // Cierra el form de guardar

                    // Segundo formulario, pero aún dentro del div de botones
                    out.println("<form action='ConsultarOficinas' method='post'>");
                    out.println("<button type='submit'>Volver</button>");
                    out.println("</form>");

                    out.println("</div>");
                } else {
                    out.println("<p>No se encontró un funcionario con ese ID.</p>");
                }

                conn.close();
            } catch (Exception e) {
                out.println("<p>Error: " + e.getMessage() + "</p>");
            }

           
            out.println("</div></body></html>");
        }
    }
}
