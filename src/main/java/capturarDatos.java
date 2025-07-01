import java.io.IOException;
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

        Connection con;
        Statement stmt;
        ResultSet rs;

        res.setContentType("text/html");
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
                // Usuario y clave correctos, redirigir a Menu.jsp
                req.getSession().setAttribute("usuario", usuario);
                res.sendRedirect("Menu.jsp");
                System.out.println("Usuario y clave correctos, redirigiendo a Menu.jsp");
            } else {
                req.setAttribute("errorLogin", "Usuario o clave incorrectos");
                req.getRequestDispatcher("Index.jsp").forward(req, res);
            }
            
        } catch (java.sql.SQLException | ClassNotFoundException e) {
            req.setAttribute("errorLogin", "Ocurrió un error: " + e.getMessage());
            req.getRequestDispatcher("index.jsp").forward(req, res);
        }
    }
}
