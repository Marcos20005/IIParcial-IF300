import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Declaración de servlet para guardar la cédula del caso en la sesión y redirigir al formulario para ingresar funcionario.
@WebServlet("/GuardarCedulaCasoEnSesion")
public class GuardarCedulaCasoEnSesion extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cedulaCaso = request.getParameter("cedulaCaso");
        HttpSession session = request.getSession(true);
        session.setAttribute("cedulaCaso", cedulaCaso);

        // Redirigir a formulario para ingresar funcionario
        response.sendRedirect("vFuncionario");
    }
}
