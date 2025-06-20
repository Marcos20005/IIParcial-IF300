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

@WebServlet("/BuscarOficina")
public class BuscarOficina extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String idFuncionario = request.getParameter("cedula");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'><head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Información de Funcionario</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head><body>");
            out.println("<div class='ventana'>");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

                String sql = "SELECT * FROM oficinaregional WHERE IDempleado = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, idFuncionario);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    out.println("<h2>Información del Funcionario</h2>");
                    out.println("<p><strong>Nombre:</strong> " + rs.getString("Nombre") + "</p>");
                    out.println("<p><strong>Cédula:</strong> " + rs.getString("Cedula") + "</p>");
                    out.println("<p><strong>ID Empleado:</strong> " + rs.getString("IDempleado") + "</p>");
                    out.println("<p><strong>Teléfono:</strong> " + rs.getString("Telefono") + "</p>");
                    out.println("<p><strong>Dirección:</strong> " + rs.getString("Direccion") + "</p>");
                    out.println("<p><strong>Lugar:</strong> " + rs.getString("Lugar") + "</p>");
                    out.println("<p><strong>Fecha Atención:</strong> " + rs.getString("FechaAtencion") + "</p>");
                    out.println("<p><strong>Hora Atención:</strong> " + rs.getString("HoraAtencion") + "</p>");
                    out.println("<p><strong>Solución:</strong> " + rs.getString("Solucion") + "</p>");
                    out.println("<p><strong>Cédula del Caso Atendido:</strong> " + rs.getString("CedulaCaso") + "</p>");
                } else {
                    out.println("<p>No se encontró un funcionario con ese ID.</p>");
                }

                conn.close();
            } catch (Exception e) {
                out.println("<p>Error al consultar: " + e.getMessage() + "</p>");
            }

            out.println("<div class='botones'>");
            out.println("<form action='ConsultarOficinas' method='post'>");
            out.println("<button type='submit'>Volver</button>");
            out.println("</form>");
            out.println("</div>");

            out.println("</div></body></html>");
        }
    }
}




