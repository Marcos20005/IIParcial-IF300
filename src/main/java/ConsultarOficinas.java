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

@WebServlet("/ConsultarOficinas")
public class ConsultarOficinas extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";



    // Método doPost para manejar la solicitud de consulta de oficinas con respuestas registradas.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Oficinas con respuestas registradas</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");
            out.println("<h2>Oficinas con respuestas registradas</h2>");
            out.println("<label for='areaOficinas'>Funcionarios registrados</label>");
            out.println("<textarea id='areaOficinas' rows='5' readonly>");


            // Consultar base de datos para obtener las oficinas regionales
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT IDempleado, Nombre, Lugar FROM oficinaregional";
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        out.println("ID Empleado: " + rs.getString("IDempleado") +
                                ", Nombre: " + rs.getString("Nombre") +
                                ", Lugar: " + rs.getString("Lugar"));
                    }
                }

                out.println("</textarea><br><br>");

                
                out.println("<label for='areaCasos'>Cédulas de casos registrados</label>");
                out.println("<textarea id='areaCasos' rows='5' readonly>");

                String sqlCasos = "SELECT Cedula FROM caso";
                try (PreparedStatement stmt2 = conn.prepareStatement(sqlCasos);
                     ResultSet rs2 = stmt2.executeQuery()) {
                     // Consultar base de datos para obtener las cédulas de casos registrados   
                    while (rs2.next()) {
                        out.println(rs2.getString("Cedula"));
                    }
                }

            } catch (SQLException e) {
                out.println("Error al consultar las oficinas o los casos: " + e.getMessage());
            }

            out.println("</textarea><br><br>");

            // CAMPO PARA CÉDULA DE CASO A RESPONDER
            out.println("<label for='cedulaCaso'>Ingrese la cédula del caso y seleccione funcion</label>");
            out.println("<input type='text' id='cedulaCaso' name='cedulaCaso'><br><br>");

            // BOTONES
            out.println("<div class='botones'>");

            // BOTÓN: INGRESAR RESPUESTA DE FUNCIONARIO
            out.println("<form action='vFuncionario' method='post' style='display:inline; margin-left:10px;'>");
            out.println("<input type='hidden' name='cedulaCaso' id='cedulaCasoOculta'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaCasoOculta').value = document.getElementById('cedulaCaso').value;\">Ingresar respuesta de funcionario</button>");
            out.println("</form>");

            // BUSCAR FUNCIONARIO
            out.println("<form action='BuscarOficina' method='post' style='display:inline; margin-left:10px;'>");
            out.println("<input type='hidden' name='cedula' id='cedulaOficinaBuscar'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaOficinaBuscar').value = document.getElementById('cedulaCaso').value;\">Buscar</button>");
            out.println("</form>");

            // EDITAR FUNCIONARIO
            out.println("<form action='EditarFuncionario' method='post' style='display:inline; margin-left:10px;'>");
            out.println("<input type='hidden' name='cedula' id='cedulaOficinaEditar'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaOficinaEditar').value = document.getElementById('cedulaCaso').value;\">Editar</button>");
            out.println("</form>");

            // ELIMINAR FUNCIONARIO
            out.println("<form action='EliminarFuncionario' method='post' style='display:inline; margin-left:10px;'>");
            out.println("<input type='hidden' name='cedula' id='cedulaOficinaEliminar'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaOficinaEliminar').value = document.getElementById('cedulaCaso').value; return confirmarEliminacion();\">Eliminar</button>");
            out.println("</form>");

            out.println("</div>");

            out.println("<script>");
            out.println("function confirmarEliminacion() {");
            out.println("  return confirm('¿Estás seguro de que deseas eliminar este funcionario?');");
            out.println("}");
            out.println("</script>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Error al cargar el driver JDBC", e);
        }
    }
}
