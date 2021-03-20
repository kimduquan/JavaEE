/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.file;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import epf.validation.file.File;

/**
 *
 * @author FOXCONN
 */
@Path("file")
public interface Files {
    
    /**
     * @param target
     * @return
     */
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    long copy(
            @MatrixParam("target")
            @NotBlank
            @File
            final String target
    );
    
    /**
     * @param path
     * @param attrs
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String createFile(
            @FormParam("path")
            @NotBlank
            final String path,
            @FormParam("attrs")
            final Map<String, String> attrs
    );
    
    /**
     * @param dir
     * @param prefix
     * @param suffix
     * @param attrs
     * @return
     */
    @POST
    @Path("temp")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String createTempFile(
            @FormParam("dir")
            final String dir,
            @FormParam("prefix")
            @NotBlank
            final String prefix,
            @FormParam("suffix")
            @NotBlank
            final String suffix,
            @FormParam("attrs")
            final Map<String, String> attrs
    );
    
    /**
     * @param path
     */
    @DELETE
    void delete(
            @MatrixParam("path")
            @NotBlank
            @File
            final String path
    );
    
    /**
     * @param start
     * @param maxDepth
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<String> find(
            @MatrixParam("start")
            @NotBlank
            @File
            String start, 
            @MatrixParam("maxDepth") 
            @Positive
            Integer maxDepth
    );
    
    /**
     * @param path
     * @return
     */
    @GET
    @Path("lines")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    StreamingOutput lines(
            @MatrixParam("path")
            @NotBlank
            @File
            final String path
    );
    
    /**
     * @param source
     * @param target
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String move(
            @FormParam("source")
            @NotBlank
            @File
            final String source, 
            @MatrixParam("target")
            @NotBlank
            final String target
    );
}
