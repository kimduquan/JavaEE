package epf.webapp;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import epf.util.SystemUtil;
import epf.util.logging.Logging;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Enumeration;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/*"}, loadOnStartup = 1)
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "EPF")
@ServletSecurity(
		@HttpConstraint(
				value = EmptyRoleSemantic.PERMIT,
				transportGuarantee = TransportGuarantee.CONFIDENTIAL,
				rolesAllowed = {epf.client.security.Security.DEFAULT_ROLE}
				)
		)
@RolesAllowed(epf.client.security.Security.DEFAULT_ROLE)
public class WebAppServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logging.getLogger(WebAppServlet.class.getName());
       
    /**
     * 
     */
    public WebAppServlet() {
        super();
    }
    
    /**
     * @param request
     * @return
     * @throws URISyntaxException
     */
    protected static URI buildRequestUri(final HttpServletRequest request) throws URISyntaxException {
    	final String portletUrl = SystemUtil.getenv(WebApp.PORTLET_URL);
    	final String requestUri = request.getRequestURI();
    	final String queryString = request.getQueryString();
    	final String uri = portletUrl + requestUri + (queryString != null ? queryString : "");
    	return new URI(uri);
    }
    
    /**
     * @param builder
     * @param request
     * @return
     */
    protected static Builder buildHeaders(final Builder builder, final HttpServletRequest request) {
    	final Enumeration<String> headerNames = request.getHeaderNames();
		Builder newBuilder = builder;
    	if(headerNames != null) {
    		while(headerNames.hasMoreElements()) {
    			final String headerName = headerNames.nextElement();
    			final String header = request.getHeader(headerName);
    			newBuilder = newBuilder.header(headerName, header);
    		}
    	}
    	return newBuilder;
    }
    
    /**
     * @param request
     * @param uri
     * @return
     */
    protected static Builder buildRequest(final HttpServletRequest request, final URI uri) {
    	Builder builder = HttpRequest.newBuilder(uri);
    	builder = buildHeaders(builder, request);
    	return builder;
    }
    
    /**
     * @return
     */
    protected static HttpClient buildClient() {
    	return HttpClient.newBuilder().build();
    }
    
    protected static void buildResponse(final HttpServletResponse response, final HttpResponse<InputStream> httpResponse) throws IOException {
    	httpResponse.body().transferTo(response.getOutputStream());
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	try {
    		final URI uri = buildRequestUri(request);
        	final HttpRequest httpRequest = buildRequest(request, uri).GET().build();
        	final HttpClient client = buildClient();
			final HttpResponse<InputStream> httpResponse = client.send(httpRequest, BodyHandlers.ofInputStream());
			buildResponse(response, httpResponse);
		}
    	catch (InterruptedException | URISyntaxException e) {
			LOGGER.throwing(getClass().getName(), "doGet", e);
		}
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	try {
    		final URI uri = buildRequestUri(request);
    		final InputStream input = request.getInputStream();
        	final HttpRequest httpRequest = buildRequest(request, uri).POST(BodyPublishers.ofInputStream(() -> input)).build();
        	final HttpClient client = buildClient();
    		final HttpResponse<InputStream> httpResponse = client.send(httpRequest, BodyHandlers.ofInputStream());
			buildResponse(response, httpResponse);
		}
    	catch (InterruptedException | URISyntaxException e) {
			LOGGER.throwing(getClass().getName(), "doPost", e);
		}
    }

    @Override
    protected void doPut(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	try {
    		final URI uri = buildRequestUri(request);
    		final InputStream input = request.getInputStream();
        	final HttpRequest httpRequest = buildRequest(request, uri).PUT(BodyPublishers.ofInputStream(() -> input)).build();
        	final HttpClient client = buildClient();
        	final HttpResponse<InputStream> httpResponse = client.send(httpRequest, BodyHandlers.ofInputStream());
			buildResponse(response, httpResponse);
		}
    	catch (InterruptedException | URISyntaxException e) {
			LOGGER.throwing(getClass().getName(), "doPut", e);
		}
    }
    
    @Override
    protected void doDelete(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	try {
    		final URI uri = buildRequestUri(request);
    		final HttpRequest httpRequest = buildRequest(request, uri).DELETE().build();
        	final HttpClient client = buildClient();
        	final HttpResponse<InputStream> httpResponse = client.send(httpRequest, BodyHandlers.ofInputStream());
			buildResponse(response, httpResponse);
		}
    	catch (InterruptedException | URISyntaxException e) {
			LOGGER.throwing(getClass().getName(), "doDelete", e);
		}
    }
}
