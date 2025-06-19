
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
        PrintWriter pw; //objeto que se utiliza para enviarle la respuesta al usuario.

        Connection con;
        Statement stmt;
        ResultSet rs;

        res.setContentType("text/html");  // se le indica al navegador el tipo de contenido que tendrá la respuesta que se enviará al cliente.
        pw = res.getWriter(); // se crea el objeto para la enviar la respuesta.

        try {
            usuario = req.getParameter("Usuario"); //recibe el usuario de la página index.html.
            clave = req.getParameter("Clave"); // recibe la clave de la página index.html.

            Class.forName("com.mysql.cj.jdbc.Driver"); // Usa el driver actualizado para MySQL 8+

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto1?verifyServerCertificate=false&useSSL=true", "root", "cRojas34");
            con.setAutoCommit(true);

            System.out.println("Conexión exitosa...");
            System.out.println("Prueba: ");
            
            stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM usuario");
            while (rs1.next()) {
                if (usuario.equals(rs1.getString("login")) && clave.equals(rs1.getString("clave"))) {
                    res.sendRedirect("Menu.html");
                    System.out.println("Usuario y clave correctos, redirigiendo a menu.html");
                } else {

                }
            }
            if (usuario != null && !usuario.equals("")) {
                query = "select * from usuario where login='" + usuario + "' and clave='" + clave + "'"; 
            }else {
                query = "select * from usuario";
            }

            System.err.println(query);

            rs = stmt.executeQuery(query);

            // el siguiente código edita una página html, para enviar a desplegar al usuario el resultado.
            pw.println("<HTML><HEAD><TITLE>Leyendo parámetros</TITLE></HEAD>");
            pw.println("<BODY BGCOLOR=\"#CCBBAA\">");
            pw.println("<H2>Leyendo los datos de la tabla USUARIOS<H2><P>");
            pw.println("<UL>\n");
            pw.println("Usuario		Clave<br><br>");
            while (rs.next()) //recorre la tabla y envía el resultado del contenido de la misma.
            {
                pw.println(rs.getString("login") + "  " + rs.getString("Clave"));
                pw.println("<br><br>");
            }
            pw.println("</BODY></HTML>");
            pw.close();
        } catch (java.sql.SQLException | ClassNotFoundException e) {
            pw.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
