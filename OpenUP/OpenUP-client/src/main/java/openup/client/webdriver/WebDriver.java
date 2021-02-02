/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.webdriver;

import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("wd")
public interface WebDriver {
    
    @POST
    @Path("session")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Session newSession(Map<String, Object> capabilities) throws WebDriverException;
    
    @DELETE
    @Path("session/{session-id}")
    void deleteSession(@PathParam("session-id") String sessionId);
    
    @GET
    @Path("status")
    @Produces(MediaType.APPLICATION_JSON)
    Status getStatus();
    
    @GET
    @Path("session/{session-id}/timeouts")
    @Produces(MediaType.APPLICATION_JSON)
    Timeouts getTimeouts(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/timeouts")
    @Consumes(MediaType.APPLICATION_JSON)
    void setTimeouts(@PathParam("session-id") String sessionId, Timeouts timeouts);
    
    @POST
    @Path("session/{session-id}/url")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void navigateTo(@PathParam("session-id") String sessionId, @FormParam("url") String url);
    
    @GET
    @Path("session/{session-id}/url")
    @Produces(MediaType.APPLICATION_JSON)
    URL getCurrentUrl(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/back")
    void back(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/forward")
    void forward(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/refresh")
    void refresh(@PathParam("session-id") String sessionId);
    
    @GET
    @Path("session/{session-id}/title")
    @Produces(MediaType.APPLICATION_JSON)
    String getTitle(@PathParam("session-id") String sessionId);
    
    @GET
    @Path("session/{session-id}/window")
    @Produces(MediaType.APPLICATION_JSON)
    String getWindowHandle(@PathParam("session-id") String sessionId);
    
    @DELETE
    @Path("session/{session-id}/window")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> closeWindow(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/window")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void switchWindow(@PathParam("session-id") String sessionId, @FormParam("handle") String handle);
    
    @GET
    @Path("session/{session-id}/window/handles")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getWindowHandles(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/window/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Window newWindow(@PathParam("session-id") String sessionId, @FormParam("type") String type);
    
    @POST
    @Path("session/{session-id}/frame")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void switchToFrame(@PathParam("session-id") String sessionId, @FormParam("id") String id);
    
    @POST
    @Path("session/{session-id}/frame/parent")
    void switchToParentFrame(@PathParam("session-id") String sessionId);
    
    @GET
    @Path("session/{session-id}/window/rect")
    @Produces(MediaType.APPLICATION_JSON)
    Rectangle getWindowRect(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/window/rect")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Rectangle setWindowRect(@PathParam("session-id") String sessionId, @FormParam("width") int width, @FormParam("height") int height, @FormParam("x") int x, @FormParam("y") int y);
    
    @POST
    @Path("session/{session-id}/window/maximize")
    @Produces(MediaType.APPLICATION_JSON)
    Rectangle maximizeWindow(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/window/minimize")
    @Produces(MediaType.APPLICATION_JSON)
    Rectangle minimizeWindow(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/window/fullscreen")
    @Produces(MediaType.APPLICATION_JSON)
    Rectangle fullscreenWindow(@PathParam("session-id") String sessionId);
    
    @POST
    @Path("session/{session-id}/element")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Element findElement(@PathParam("session-id") String sessionId, @FormParam("using") String using, @FormParam("value") String value);
    
    @POST
    @Path("session/{session-id}/elements")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    List<Element> findElements(@PathParam("session-id") String sessionId, @FormParam("using") String using, @FormParam("value") String value);
    
    @POST
    @Path("session/{session-id}/element/{element-id}/element")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Element findElementFromElement(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId, @FormParam("using") String using, @FormParam("value") String value);
    
    @POST
    @Path("session/{session id}/element/{element id}/elements")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    List<Element> findElementsFromElement(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId, @FormParam("using") String using, @FormParam("value") String value);
    
    @GET
    @Path("session/{session-id}/element/active")
    @Produces(MediaType.APPLICATION_JSON)
    Element getActiveElement(@PathParam("session-id") String sessionId);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/selected")
    boolean isElementSelected(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/attribute/{name}")
    String getElementAttribute(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId, @PathParam("name") String name);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/property/{name}")
    String getElementProperty(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId, @PathParam("name") String name);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/css/{property-name}")
    String getElementCssValue(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId, @PathParam("property-name") String propertyName);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/text")
    String getElementText(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/name")
    String getElementTagName(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/rect")
    Rectangle getElementRect(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/enabled")
    boolean isElementEnabled(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/computedrole")
    String getComputedRole(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("session/{session-id}/element/{element-id}/computedlabel")
    String getComputedLabel(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @POST
    @Path("session/{session-id}/element/{element-id}/click")
    void click(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @POST
    @Path("session/{session-id}/element/{element-id}/clear")
    void clear(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @POST
    @Path("session/{session-id}/element/{element-id}/value")
    void sendKeys(@PathParam("session-id") String sessionId, @PathParam("element-id") String elementId);
    
    @GET
    @Path("session/{session-id}/source")
    @Produces(MediaType.TEXT_PLAIN)
    String getPageSource(@PathParam("session-id") String sessionId);
    
    
}
