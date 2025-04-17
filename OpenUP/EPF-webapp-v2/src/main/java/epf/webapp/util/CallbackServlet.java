package epf.webapp.util;

import java.io.IOException;
import java.util.Optional;
import jakarta.inject.Inject;
import jakarta.security.enterprise.authentication.mechanism.http.openid.OpenIdConstant;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Callback")
public class CallbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
	@Inject
    private OpenIdContext context;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        if (context != null) {
        	final Optional<String> originalRequest = context.getStoredValue(request, response, OpenIdConstant.ORIGINAL_REQUEST);
        	final  String originalRequestString = originalRequest.get();
            response.sendRedirect(originalRequestString);
        }
    }
}
