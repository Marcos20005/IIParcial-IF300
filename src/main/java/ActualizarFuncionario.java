import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Declaracion de servlet para actualizar la información de un funcionario en la base de datos.
@WebServlet("/ActualizarFuncionario")
public class ActualizarFuncionario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "cRojas34";



    // Uso del método doPost para manejar la solicitud de actualización del funcionario.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        //Declaracion de los parámetros recibidos desde el formulario de editar.
        String idEmpleado = request.getParameter("idEmpleado");
        String nombre = request.getParameter("nombre");
        String cedulaFuncionario = request.getParameter("cedulaFuncionario");
        String solucion = request.getParameter("solucion");
        String lugar = request.getParameter("lugar");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String cedulaCaso = request.getParameter("cedulaCaso");



        //Se empieza la conexion con la base de datos y actualizar los datos.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "UPDATE oficinaregional SET Nombre=?, Cedula=?, Solucion=?, Lugar=?, Direccion=?, Telefono=? WHERE IDempleado=?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, nombre);
                    stmt.setString(2, cedulaFuncionario);
                    stmt.setString(3, solucion);
                    stmt.setString(4, lugar);
                    stmt.setString(5, direccion);
                    stmt.setString(6, telefono);
                    stmt.setString(7, idEmpleado);

                    int filas = stmt.executeUpdate();
                    if (filas > 0) {
                        response.sendRedirect("ConsultarOficinas");
                    } else {
                        response.getWriter().println("<p>No se pudo actualizar el funcionario.</p>");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error al actualizar funcionario: " + e.getMessage(), e);
        }
    }
}


