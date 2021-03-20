/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.file;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import epf.validation.file.Directory;

/**
 *
 * @author FOXCONN
 */
@Path("file/directory")
public interface Directories {
    
    /**
     * @param dir
     * @param attrs
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String createDirectories(
            @FormParam("dir")
            @NotBlank
            @Directory
            final String dir,
            @FormParam("attrs")
            final Map<String, String> attrs
    );
    
    /**
     * @param dir
     * @param prefix
     * @param attrs
     * @return
     */
    @POST
    @Path("temp")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String createTempDirectory(
            @FormParam("dir")
            final String dir,
            @FormParam("prefix") 
            @NotBlank
            final String prefix,
            @FormParam("attrs")
            final Map<String, String> attrs
    );
    
    /**
     * @param dir
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<String> list(
            @MatrixParam("dir")
            @NotBlank
            @Directory
            final String dir
    );
}
