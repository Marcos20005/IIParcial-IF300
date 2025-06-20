
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//Declaración de servlet para mostrar el formulario de registro de un funcionario asociado a un caso específico.
@WebServlet("/vFuncionario")
public class vFuncionario extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cedulaCaso = request.getParameter("cedulaCaso");

        response.setContentType("text/html;charset=UTF-8");

        // Se inicia la respuesta al usuario.
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"es\"><head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Agregar Funcionario</title>");
            out.println("<link rel=\"stylesheet\" href=\"estilo.css\">");
            out.println("</head><body>");
            out.println("<div class=\"formulario-detalle ventana\">");
            out.println("<h2>Registre la información del funcionario</h2>");
            out.println("<form action=\"GuardarFuncionario\" method=\"post\">");

            // Info funcionario
            out.println("<label>Nombre del funcionario *</label>");
            out.println("<input type=\"text\" name=\"nombre\" required>");
            out.println("<label>Cédula del funcionario *</label>");
            out.println("<input type=\"text\" name=\"cedulaFuncionario\" required>");
            out.println("<label>Código del funcionario *</label>");
            out.println("<input type=\"text\" name=\"idEmpleado\" required>");
            out.println("<label>Solución propuesta *</label>");
            out.println("<textarea name=\"solucion\" required></textarea>");

            // Info oficina
            out.println("<label>Lugar de oficina *</label>");
            out.println("<input type=\"text\" name=\"lugar\" required>");
            out.println("<label>Teléfono oficina *</label>");
            out.println("<input type=\"text\" name=\"telefono\" required>");
            out.println("<label>Dirección oficina *</label>");
            out.println("<input type=\"text\" name=\"direccion\" required>");

            // Cédula del caso oculta
            out.printf("<input type=\"hidden\" name=\"cedulaCaso\" value=\"%s\">", cedulaCaso);

            out.println("<div class=\"botones\">");
            out.println("<button type=\"submit\">💾 Guardar</button>");
            out.println("</div>");
            out.println("</form></div></body></html>");
        }
    }
}
