import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/vFuncionario")
public class vFuncionario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String cedulaCaso = (session != null) ? (String) session.getAttribute("cedulaCaso") : "";

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"es\"><head>");
            out.println("  <meta charset=\"UTF-8\">");
            out.println("  <title>Agregar Funcionario</title>");
            out.println("  <link rel=\"stylesheet\" href=\"estilo.css\">");
            out.println("</head><body>");
            out.println("<div class=\"formulario-detalle ventana\" style=\"display: block;\">");
            out.println("  <h2>Registre la información solicitada</h2>");
            out.println("  <form action=\"http://localhost:8080/miproyectoexamen/GuardarFuncionario\" method=\"post\">");

            out.println("    <div class=\"fila-doble\">");

            // Columna izquierda
            out.println("    <div class=\"columna\">");
            out.println("      <p><strong>Información del funcionario:</strong></p>");
            out.println("      <label>Nombre del funcionario *</label>");
            out.println("      <input type=\"text\" name=\"nombre\" required>");
            out.println("      <label>Cédula del funcionario *</label>");
            out.println("      <input type=\"text\" name=\"cedulaFuncionario\" required>");
            out.println("      <label>Código del funcionario *</label>");
            out.println("      <input type=\"text\" name=\"idEmpleado\" required>");
            out.println("      <label>Solución propuesta para el caso *</label>");
            out.println("      <textarea name=\"solucion\" required></textarea>");
            out.println("    </div>");

            // Columna derecha
            out.println("    <div class=\"columna\">");
            out.println("      <p><strong>Información de la oficina:</strong></p>");
            out.println("      <label>Lugar de la oficina *</label>");
            out.println("      <input type=\"text\" name=\"lugar\" required>");
            out.println("      <label>Número de teléfono de la oficina *</label>");
            out.println("      <input type=\"text\" name=\"telefono\" required>");
            out.println("      <label>Dirección de la oficina *</label>");
            out.println("      <input type=\"text\" name=\"direccion\" required>");
            out.println("    </div>");

            out.println("  </div>");

            // Aquí colocamos la cédula del caso correctamente
            out.printf("  <input type=\"hidden\" name=\"cedulaCaso\" value=\"%s\">%n", cedulaCaso);

            out.println("  <div class=\"botones\">");
            out.println("    <button type=\"submit\">💾 Guardar</button>");
            out.println("  </div>");

            out.println("  </form>");
            out.println("</div>");
            out.println("</body></html>");
        }
    }
}
