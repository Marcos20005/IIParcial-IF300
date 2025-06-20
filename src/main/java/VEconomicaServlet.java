
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


// Declaración de servlet para manejar la información adicional del caso de violencia económica.
@WebServlet("/vEconomica")
public class VEconomicaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener cédula desde sesión
        HttpSession session = request.getSession(false);
        String cedula = (session != null)
                ? (String) session.getAttribute("cedulaCaso")
                : null;
        String tipoViolencia = (session != null)
                ? (String) session.getAttribute("tipoViolencia")
                : null;


        response.setContentType("text/html;charset=UTF-8");
        // Inicia la respuesta al usuario.
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"es\">");
            out.println("<head>");
            out.println("    <meta charset=\"UTF-8\">");
            out.println("    <title>Violencia Económica</title>");
            out.println("    <link rel=\"stylesheet\" href=\"estilo.css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("   <div class=\"Violencia-Economica ventana\" style=\"display: block;\">");
            out.println("    <h2>Información adicional del caso de violencia económica</h2>");
            out.println("    <form action=\"http://localhost:8080/miproyectoexamen/AgregarViolencia\" method=\"post\">");

            out.println("        <label for=\"ingreso\">Ingrese el tipo de ingreso afectado *</label>");
            out.println("        <input type=\"text\" id=\"ingreso\" name=\"ingreso\" required>");

            out.println("        <label for=\"impacto\">Ingrese el impacto económico que tuvo la persona *</label>");
            out.println("        <input type=\"text\" id=\"impacto\" name=\"impacto\" required>");

            out.println("        <label for=\"nombre\">Ingrese el nombre del agresor *</label>");
            out.println("        <input type=\"text\" id=\"nombre\" name=\"nombre\" required>");

            out.println("        <label for=\"relacion\">Ingrese la relación con el agresor *</label>");
            out.println("        <input type=\"text\" id=\"relacion\" name=\"relacion\" required>");

            out.println("        <label>Género de la persona *</label>");
            out.println("        <div class=\"radio-grupo\">");
            out.println("            <label><input type=\"radio\" name=\"genero\" value=\"Femenino\" required> Femenino</label>");
            out.println("            <label><input type=\"radio\" name=\"genero\" value=\"Masculino\" required> Masculino</label>");
            out.println("            <label><input type=\"radio\" name=\"genero\" value=\"Otro\" required> Otro</label>");
            out.println("        </div>");

            // Campo oculto con la cédula
            out.printf("        <input type=\"hidden\" name=\"cedula\" value=\"%s\">%n",
                    cedula != null ? cedula : "");
            out.printf("        <input type=\"hidden\" name=\"tipoViolencia\" value=\"%s\">%n",
                    tipoViolencia != null ? tipoViolencia : "");

            out.println("        <div class=\"botones\">");
            out.println("            <button type=\"submit\">💾Guardar</button>");
            out.println("        </div>");

            out.println("    </form>");
            out.println("   </div>");
            out.println("</body>");
            out.println("</html>");

        }
    }
}
