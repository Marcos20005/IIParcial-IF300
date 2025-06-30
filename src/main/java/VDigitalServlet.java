import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


// Declaración de servlet para manejar la información adicional del caso de Violencia Digital.
@WebServlet("/vDigital")
public class VDigitalServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)       
            throws ServletException, IOException {

        // Recupera la cédula desde la sesión
        HttpSession session = request.getSession(false);
        String cedula = (session != null)
                ? (String) session.getAttribute("CedulaCaso")
                : null;
        String tipoViolencia = (session != null)
                ? (String) session.getAttribute("tipoViolencia")
                : null;

        // Por si acaso, pasa los valores también como atributos
        request.setAttribute("cedula", cedula);
        request.setAttribute("tipoViolencia", tipoViolencia);

        // Redirige al JSP en lugar de imprimir HTML
        request.getRequestDispatcher("vDigital.jsp").forward(request, response);
    }
}
