
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/capturarDatos")
public class capturarDatos extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String usuario;
        String clave;
        String query;
        PrintWriter pw;

        Connection con;
        Statement stmt;
        ResultSet rs;

        res.setContentType("text/html");
        pw = res.getWriter();

        try {
            usuario = req.getParameter("Usuario");
            clave = req.getParameter("Clave");

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1?verifyServerCertificate=false&useSSL=true", "root", "cRojas34");
            con.setAutoCommit(true);

            stmt = con.createStatement();
            query = "SELECT * FROM usuario WHERE login='" + usuario + "' AND clave='" + clave + "'";
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                // Usuario y clave correctos
                res.sendRedirect("Menu.html");
                System.out.println("Usuario y clave correctos, redirigiendo a Menu.html");
            } else {
                // Usuario o clave incorrectos: mensaje bonito con botón
                pw.println("<!DOCTYPE html>");
                pw.println("<html lang='es'>");
                pw.println("<head>");
                pw.println("<meta charset='UTF-8'>");
                pw.println("<title>Error de autenticación</title>");
                pw.println("<link rel='stylesheet' href='styles.css'>");
                pw.println("</head>");
                pw.println("<body>");
                pw.println("<div class='contenedor' style='text-align: center;'>");
                pw.println("<h2>Usuario o contraseña incorrectos</h2>");
                pw.println("<form action='Index.html' method='get'>");
                pw.println("<button type='submit'>Volver a intentar</button>");
                pw.println("</form>");
                pw.println("</div>");
                pw.println("</body>");
                pw.println("</html>");
            }

            pw.close();
        } catch (java.sql.SQLException | ClassNotFoundException e) {
            pw.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
