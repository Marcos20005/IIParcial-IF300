import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ConsultarCasos")
public class ConsultarCasos extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Define your DB credentials here
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "erpalacios";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");  
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

                        String sql = "SELECT Cedula, Nombre, Descripcion  FROM caso";
                        try(PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()){

                            List<String> casos = new ArrayList<>();
                            while (rs.next()) {
                                String cedula = rs.getString("Cedula");
                                String nombre = rs.getString("Nombre");
                                String descripcion = rs.getString("Descripcion");
                                casos.add("Cédula: " + cedula + ", Nombre: " + nombre + ", Descripción: " + descripcion);
                            }
                            request.setAttribute("listaCasos", casos);
                            request.getRequestDispatcher("casos.jsp").forward(request, response);
                        }
                        }
                    } catch (ClassNotFoundException | SQLException e){
                        throw new ServletException("Error al consultar casos", e);
                    }
                }
                    }
                