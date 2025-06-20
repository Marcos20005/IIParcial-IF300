import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Declaración de servlet para manejar la información adicional del caso de violencia emocional.
@WebServlet("/vEmocional")
public class vEmocional extends HttpServlet {

    private static final long serialVersionUID = 1L;


    // Uso del método doPost para manejar la solicitud de información adicional del caso de violencia emocional.
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera la cédula y tipo de violencia desde la sesión
        HttpSession session = request.getSession(false);
        String cedula = (session != null) ? (String) session.getAttribute("cedulaCaso") : "";
        String tipoViolencia = (session != null) ? (String) session.getAttribute("tipoViolencia") : "";

        response.setContentType("text/html;charset=UTF-8");

        // Se inicia la respuesta al usuario.
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"es\"><head>");
            out.println("  <meta charset=\"UTF-8\">");
            out.println("  <title>Violencia Emocional</title>");
            out.println("  <link rel=\"stylesheet\" href=\"estilo.css\">");
            out.println("</head><body>");
            out.println("  <div class=\"Violencia-Emocional ventana\">");
            out.println("    <h2>Información adicional del caso de Violencia Emocional</h2>");
            out.println("    <form action=\"http://localhost:8080/miproyectoexamen/AgregarViolencia\" method=\"post\">");

            out.println("      <label for=\"impacto\">Impacto psicológico *</label>");
            out.println("      <input type=\"text\" id=\"impacto\" name=\"impacto\" required>");

            out.println("      <label for=\"nombre\">Nombre del agresor *</label>");
            out.println("      <input type=\"text\" id=\"nombre\" name=\"nombre\" required>");

            out.println("      <label for=\"relacion\">Relación con el agresor *</label>");
            out.println("      <input type=\"text\" id=\"relacion\" name=\"relacion\" required>");

            out.println("      <label>Género del agresor *</label>");
            out.println("      <div class=\"radio-grupo\">");
            out.println("        <label><input type=\"radio\" name=\"genero\" value=\"Femenino\" required> Femenino</label>");
            out.println("        <label><input type=\"radio\" name=\"genero\" value=\"Masculino\" required> Masculino</label>");
            out.println("        <label><input type=\"radio\" name=\"genero\" value=\"Otro\" required> Otro</label>");
            out.println("      </div>");

            // Campos ocultos
            out.printf("      <input type=\"hidden\" name=\"cedula\" value=\"%s\">%n", cedula);
            out.printf("      <input type=\"hidden\" name=\"tipoViolencia\" value=\"%s\">%n", tipoViolencia);

            out.println("      <div class=\"botones\">");
            out.println("        <button type=\"submit\">💾 Guardar</button>");
            out.println("      </div>");

            out.println("    </form>");
            out.println("  </div>");
            out.println("</body></html>");
        }
    }
}
