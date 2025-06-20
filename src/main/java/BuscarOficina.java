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

@WebServlet("/BuscarOficina")
public class BuscarOficina extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String idBuscado = request.getParameter("cedula"); // ID del funcionario

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Resultado de búsqueda</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");
            out.println("<h2>Información de la oficina</h2>");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    String sql = "SELECT * FROM oficinaregional WHERE IDempleado = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, idBuscado);
                        try (ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) {
                                out.println("<p><strong>ID Empleado:</strong> " + rs.getString("IDempleado") + "</p>");
                                out.println("<p><strong>Nombre:</strong> " + rs.getString("Nombre") + "</p>");
                                out.println("<p><strong>Cédula Funcionario:</strong> " + rs.getString("Cedula") + "</p>");
                                out.println("<p><strong>Lugar:</strong> " + rs.getString("Lugar") + "</p>");
                                out.println("<p><strong>Dirección:</strong> " + rs.getString("Direccion") + "</p>");
                                out.println("<p><strong>Teléfono:</strong> " + rs.getString("Telefono") + "</p>");
                                out.println("<p><strong>Fecha de Atención:</strong> " + rs.getString("FechaAtencion") + "</p>");
                                out.println("<p><strong>Hora de Atención:</strong> " + rs.getString("HoraAtencion") + "</p>");
                                out.println("<p><strong>Solución:</strong> " + rs.getString("Solucion") + "</p>");
                                out.println("<p><strong>Cédula del Caso:</strong> " + rs.getString("CedulaCaso") + "</p>");
                            } else {
                                out.println("<p>No se encontró una oficina con ese ID de empleado.</p>");
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                out.println("<p>Error en la base de datos: " + e.getMessage() + "</p>");
            } catch (ClassNotFoundException e) {
                throw new ServletException("No se pudo cargar el driver JDBC", e);
            }

            // Botones
            out.println("<div class='botones'>");
            out.println("<form action='ConsultarOficinas' method='post' style='display:inline;'>");
            out.println("<button type='submit'>Volver a lista de oficinas</button>");
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



