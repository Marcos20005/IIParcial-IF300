import java.io.IOException;
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

@WebServlet("/BuscarCaso")
public class BuscarCaso extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto1";
    private static final String USER = "root";
    private static final String PASSWORD = "cRojas34";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String cedulaBuscada = request.getParameter("cedula");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

                // Buscar caso
                String sqlCaso = "SELECT * FROM caso WHERE Cedula = ?";
                try (PreparedStatement stmtCaso = conn.prepareStatement(sqlCaso)) {
                    stmtCaso.setString(1, cedulaBuscada);
                    try (ResultSet rs = stmtCaso.executeQuery()) {
                        if (rs.next()) {
                            request.setAttribute("cedula", rs.getString("Cedula"));
                            request.setAttribute("nombre", rs.getString("Nombre"));
                            request.setAttribute("descripcion", rs.getString("Descripcion"));
                            request.setAttribute("fecha", rs.getString("Fecha"));
                            request.setAttribute("numeroCelular", rs.getString("NumeroCelular"));
                            request.setAttribute("direccion", rs.getString("Direccion"));
                            request.setAttribute("edad", rs.getString("Edad"));
                            request.setAttribute("genero", rs.getString("Genero"));
                            request.setAttribute("estadoCivil", rs.getString("EstadoCivil"));
                            request.setAttribute("ocupacion", rs.getString("Ocupacion"));
                            request.setAttribute("nacionalidad", rs.getString("Nacionalidad"));
                            request.setAttribute("agresor", rs.getString("Agresor"));
                            request.setAttribute("relacionAgresor", rs.getString("RelacionAgresor"));
                            request.setAttribute("generoAgresor", rs.getString("GeneroAgresor"));

                            String tipoViolencia = rs.getString("TipoViolencia");
                            request.setAttribute("tipoViolencia", tipoViolencia);

                            switch (tipoViolencia) {
                                case "Violencia Económica":
                                    request.setAttribute("tipoIngreso", rs.getString("TipoIngreso"));
                                    request.setAttribute("cantidadIngreso", rs.getString("CantidadIngreso"));
                                    break;
                                case "Violencia Física":
                                    request.setAttribute("tipoLesion", rs.getString("TipoLesion"));
                                    request.setAttribute("atencionMedica", rs.getString("AtencionMedica"));
                                    break;
                                case "Violencia Emocional":
                                    request.setAttribute("impactoPsicologico", rs.getString("ImpactoPsicologico"));
                                    break;
                                case "Violencia Digital":
                                    request.setAttribute("plataformaDigital", rs.getString("PlataformaDigital"));
                                    break;
                                case "Violencia Sexual":
                                    request.setAttribute("tipoAbusoSexual", rs.getString("TipoAbusoSexual"));
                                    break;
                            }
                        } else {
                            request.setAttribute("noEncontrado", true);
                        }
                    }
                }

                // Buscar funcionario asignado
                String sqlFunc = "SELECT * FROM oficinaregional WHERE CedulaCaso = ?";
                try (PreparedStatement stmtFunc = conn.prepareStatement(sqlFunc)) {
                    stmtFunc.setString(1, cedulaBuscada);
                    try (ResultSet rsFunc = stmtFunc.executeQuery()) {
                        if (rsFunc.next()) {
                            request.setAttribute("funcNombre", rsFunc.getString("Nombre"));
                            request.setAttribute("funcCedula", rsFunc.getString("Cedula"));
                            request.setAttribute("funcID", rsFunc.getString("IDempleado"));
                            request.setAttribute("funcTelefono", rsFunc.getString("Telefono"));
                            request.setAttribute("funcDireccion", rsFunc.getString("Direccion"));
                            request.setAttribute("funcLugar", rsFunc.getString("Lugar"));
                            request.setAttribute("funcFechaAtencion", rsFunc.getString("FechaAtencion"));
                            request.setAttribute("funcHoraAtencion", rsFunc.getString("HoraAtencion"));
                            request.setAttribute("funcSolucion", rsFunc.getString("Solucion"));
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            request.setAttribute("error", "Error al buscar el caso: " + e.getMessage());
        }

        request.getRequestDispatcher("resulBuscarCaso.jsp").forward(request, response);
    }
}
