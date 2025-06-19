import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/vUsuario")
public class vUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Nuevo Usuario</title>");
            out.println("<link rel='stylesheet' href='estilo.css'>");
            out.println("</head><body>");

            out.println("<div class='Nuevo-Usuario ventana' style='display: block;'>");
            out.println("<h2>Información para crear nuevo usuario</h2>");
            out.println("<form action='http://localhost:8080/miproyectoexamen/GuardarUsuario' method='post'>");

            out.println("<label>Cédula *</label>");
            out.println("<input type='text' name='cedula' required>");

            out.println("<label>Primer nombre *</label>");
            out.println("<input type='text' name='nombre' required>");

            out.println("<label>Segundo nombre</label>");
            out.println("<input type='text' name='nombre2'>");

            out.println("<label>Primer apellido *</label>");
            out.println("<input type='text' name='apellido' required>");

            out.println("<label>Segundo apellido</label>");
            out.println("<input type='text' name='apellido2'>");

            out.println("<label>Nombre de usuario *</label>");
            out.println("<input type='text' name='login' required>");

            out.println("<label>Contraseña *</label>");
            out.println("<input type='password' name='clave' required>");

            out.println("<div class='botones'>");
            out.println("<button type='submit'>💾 Guardar</button>");
            out.println("</div>");

            out.println("</form>");
            out.println("</div>");
            out.println("</body></html>");
        }
    }
}
