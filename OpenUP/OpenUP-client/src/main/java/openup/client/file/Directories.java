/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.file;

import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("file/directory")
public interface Directories {
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String createDirectories(
            @FormParam("dir")
            String dir,
            @FormParam("attrs")
            Map<String, String> attrs
    ) throws Exception;
    
    @POST
    @Path("temp")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String createTempDirectory(
            @FormParam("dir")
            String dir,
            @FormParam("prefix")
            String prefix,
            @FormParam("attrs")
            Map<String, String> attrs
    ) throws Exception;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<String> list(
            @MatrixParam("dir")
            String dir
    ) throws Exception;
}
