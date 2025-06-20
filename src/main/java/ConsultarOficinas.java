
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

            out.println("<label for='areaCasos'>oficinas registradas</label>");
            out.println("<textarea id='areaCasos' rows='5' readonly>");

            // Conexión y consulta
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT IDempleado, Nombre, Lugar FROM oficinaregional";
                try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        String idEmpleado = rs.getString("IDempleado");
                        String lugar = rs.getString("Lugar");
                        String nombre = rs.getString("Nombre");

                        out.println("ID Empleado: " + idEmpleado + ", Nombre:" + nombre + ", Lugar: " + lugar);
                        out.println(); // salto de línea
                    }
                }
            } catch (SQLException e) {
                out.println("Error al consultar las oficinas: " + e.getMessage());
            }

            out.println("</textarea>");

            out.println("<label for='cedula'>Ingrese ID del funcionario de oficina</label>");
            out.println("<input type='text' id='cedula' name='cedula'>");

            out.println("<div class='botones'>");
            out.println("<form action=\"vFuncionario\" method=\"post\" style=\"display:inline;\">");
            out.println("<button type=\"submit\">Ingresar respuesta de funcionario</button>");
            out.println("</form>");

            out.println("<form action='BuscarOficina' method='post'>");
            out.println("<input type='hidden' name='cedula' id='cedulaOficinaOculta'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaOficinaOculta').value = document.getElementById('cedula').value;\">Buscar</button>");
            out.println("</form>");

            out.println("<form action='EditarFuncionario' method='post'>");
            out.println("<input type='hidden' name='cedula' id='cedulaOficinaEditar'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaOficinaEditar').value = document.getElementById('cedula').value;\">Editar</button>");
            out.println("</form>");

            out.println("<form action='EliminarFuncionario' method='post'>");
            out.println("<input type='hidden' name='cedula' id='cedulaOficinaEliminar'>");
            out.println("<button type='submit' onclick=\"document.getElementById('cedulaOficinaEliminar').value = document.getElementById('cedula').value; return confirmarEliminacion();\">Eliminar</button>");
            out.println("</form>");

            out.println("<script>");
            out.println("function confirmarEliminacion() {");
            out.println("  return confirm('¿Estás seguro de que desea eliminar este funcionario?');");
            out.println("}");
            out.println("</script>");
            
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException e) {
            throw new ServletException("Error al cargar el driver JDBC", e);
        }
    }
}
