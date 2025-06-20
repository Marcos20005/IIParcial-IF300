import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ActualizarFuncionario")
public class ActualizarFuncionario extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String idEmpleado = request.getParameter("idEmpleado");
        String nombre = request.getParameter("Nombre");
        String lugar = request.getParameter("Lugar");
        String direccion = request.getParameter("Direccion");
        String telefono = request.getParameter("Telefono");
        String cedula = request.getParameter("Cedula");
        String solucion = request.getParameter("Solucion");
        String cedulaCaso = request.getParameter("CedulaCaso");

        String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Actualizar Funcionario</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='ventana'>");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

                    String sql = "UPDATE oficinaregional SET Nombre=?, Lugar=?, Direccion=?, Telefono=?, Cedula=?, Solucion=?, CedulaCaso=?, HoraAtencion=? WHERE IDempleado=?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, nombre);
                        stmt.setString(2, lugar);
                        stmt.setString(3, direccion);
                        stmt.setString(4, telefono);
                        stmt.setString(5, cedula);
                        stmt.setString(6, solucion);
                        stmt.setString(7, cedulaCaso);
                        stmt.setString(8, horaActual);
                        stmt.setString(9, idEmpleado);

                        int filas = stmt.executeUpdate();

                        if (filas > 0) {
                            out.println("<p>✅ Funcionario actualizado correctamente.</p>");
                        } else {
                            out.println("<p>❌ No se pudo actualizar el funcionario. ID no encontrado.</p>");
                        }
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                out.println("<p>Error en la base de datos: " + e.getMessage() + "</p>");
            }

            // Botones alineados igual que ActualizarCaso
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

