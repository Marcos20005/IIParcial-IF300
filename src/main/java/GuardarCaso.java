import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/GuardarCaso")
public class GuardarCaso extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "cRojas34";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String descripcion = request.getParameter("txtDescripcionDenuncia");
        String nombre = request.getParameter("txtNombre");
        int edad = Integer.parseInt(request.getParameter("txtedad"));
        String direccion = request.getParameter("txtDireccion");
        String ocupacion = request.getParameter("txtOcupacion");
        String estadoCivil = request.getParameter("boxEstado");
        String cedula = request.getParameter("txtCedula");
        String celular = request.getParameter("txtCelular");
        String genero = request.getParameter("genero");
        String nacionalidad = request.getParameter("txtNacionalidad");
        String tipoViolencia = request.getParameter("tipoViolencia");

        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                Statement stmt = conn.createStatement();

              
                String sql = "INSERT INTO caso (Descripcion, Nombre, Edad, Direccion, Ocupacion, EstadoCivil, Cedula, NumeroCelular, Genero, Nacionalidad, TipoViolencia, Resuelto) " +
                             "VALUES ('" + descripcion + "', '" + nombre + "', " + edad + ", '" + direccion + "', '" + ocupacion + "', '" + estadoCivil + "', '" +
                             cedula + "', '" + celular + "', '" + genero + "', '" + nacionalidad + "', '" + tipoViolencia + "', 'False')";

                int filasAfectadas = stmt.executeUpdate(sql);

                if (filasAfectadas > 0) {
System.out.println(">>> Caso guardado exitosamente.");
                    if (tipoViolencia.equals("Violencia Digital")) {
                        System.out.println(">>> Redirigiendo a vDigital.html");
                    // tras guardar el caso y guardar la cédula en sesión...
HttpSession session = request.getSession();
session.setAttribute("cedulaCaso", cedula);
 session.setAttribute("tipoViolencia", tipoViolencia);
// despacha la petición a /vDigital (el nuevo servlet)
request.getRequestDispatcher("/vDigital").forward(request, response);
return; 
                    }else if (tipoViolencia.equals("Violencia Económica")) {
         System.out.println(">>> Redirigiendo a vEconomica.html");
                    // tras guardar el caso y guardar la cédula en sesión...
HttpSession session = request.getSession();
session.setAttribute("cedulaCaso", cedula);
 session.setAttribute("tipoViolencia", tipoViolencia);
// despacha la petición a /vDigital (el nuevo servlet)
request.getRequestDispatcher("/vEconomica").forward(request, response);
return; 
                      
                    } else if (tipoViolencia.equals("Violencia Psicologica")) {
                        response.sendRedirect("vPsicologica.html");
                    } else if (tipoViolencia.equals("Violencia Sexual")) {
                        response.sendRedirect("vSexual.html");
                    } else if (tipoViolencia.equals("Violencia Fisica")) {
                        response.sendRedirect("vEconomica.html");
                    }
                    out.println("<h3>¡Caso guardado exitosamente!</h3>");
                    System.out.println(">>> tipoViolencia recibido: [" + tipoViolencia + "]");
                  
                } else {
                    out.println("<h3>Error al guardar el caso.</h3>");
                }

            } catch (SQLException e) {
                out.println("<h3>Ocurrió un error SQL: " + e.getMessage() + "</h3>");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
