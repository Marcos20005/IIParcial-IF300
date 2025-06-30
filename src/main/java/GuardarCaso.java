import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Declaración de servlet para guardar un caso en la base de datos.
@WebServlet("/GuardarCaso")
public class GuardarCaso extends HttpServlet {

    private static final long serialVersionUID = 1L;


    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";


    // Uso del método doPost para manejar la solicitud de guardar un caso.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Obtención de los parámetros del formulario
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

        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDate soloFecha = fechaActual.toLocalDate();

        try { Class.forName("com.mysql.cj.jdbc.Driver");

            // Se inicia la conexión a la base de datos y se prepara la sentencia SQL para insertar el caso.
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                Statement stmt = conn.createStatement();

                String sql = "INSERT INTO caso (Descripcion, Nombre, Edad, Direccion, Ocupacion, EstadoCivil, Cedula, NumeroCelular, Genero, Nacionalidad, TipoViolencia, Resuelto, Fecha) "
                        + "VALUES ('" + descripcion + "', '" + nombre + "', " + edad + ", '" + direccion + "', '" + ocupacion + "', '" + estadoCivil + "', '"
                        + cedula + "', '" + celular + "', '" + genero + "', '" + nacionalidad + "', '" + tipoViolencia + "','False' ,'" + soloFecha + "')";

                int filasAfectadas = stmt.executeUpdate(sql);

                if (filasAfectadas > 0) {
                     // tras guardar el caso y guardar la cédula en sesión...
                    HttpSession session = request.getSession();
                    session.setAttribute("CedulaCaso", cedula);
                    session.setAttribute("tipoViolencia", tipoViolencia);
                       
                        switch (tipoViolencia) {
                            case "Violencia Digital":
                            request.getRequestDispatcher("/vDigital").forward(request, response);
                            return;
                            case "Violencia Económica":
                             request.getRequestDispatcher("/vEconomica").forward(request, response);
                            return;
                            case "Violencia Emocional":
                            request.getRequestDispatcher("/vEmocional").forward(request, response);
                            return;
                            case "Violencia Física":
                            request.getRequestDispatcher("/vFisica").forward(request, response);
                            return;
                            case "Violencia Sexual":
                            request.getRequestDispatcher("/vSexual").forward(request, response);
                            return;
                        }
                        //Si el caso coincide
                        request.setAttribute("informar", "Caso guardado exitosamente!");
                        request.getRequestDispatcher("resultadoCaso.jsp").forward(request, response);
                    }else{
                        request.setAttribute("informar", "Error al guardar el caso!");
                        request.getRequestDispatcher("resultadoCaso.jsp").forward(request, response);
                    }       
                }catch (SQLException e){
                    request.setAttribute("mensaje", "Error SQL: " + e.getMessage());
                    request.getRequestDispatcher("resultadoCaso.jsp").forward(request, response);
                }
                }catch (ClassNotFoundException e){
                    request.setAttribute("informar", "Error al cargar la base de datos!");
                    request.getRequestDispatcher("resultadoCaso.jsp").forward(request, response);
                }
            }
        }
    

                 
                       