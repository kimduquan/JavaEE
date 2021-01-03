/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.file;

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
import openup.client.file.validation.File;

/**
 *
 * @author FOXCONN
 */
@Path("file")
public interface Files {
    
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    long copy(
            @MatrixParam("target")
            @NotBlank
            @File
            String target
    ) throws Exception;
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String createFile(
            @FormParam("path")
            @NotBlank
            String path,
            @FormParam("attrs")
            Map<String, String> attrs
    ) throws Exception;
    
    @POST
    @Path("temp")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String createTempFile(
            @FormParam("dir")
            String dir,
            @FormParam("prefix")
            @NotBlank
            String prefix,
            @FormParam("suffix")
            @NotBlank
            String suffix,
            @FormParam("attrs")
            Map<String, String> attrs
    ) throws Exception;
    
    @DELETE
    void delete(
            @MatrixParam("path")
            @NotBlank
            @File
            String path
    ) throws Exception;
    
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
    ) throws Exception;
    
    @GET
    @Path("lines")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    StreamingOutput lines(
            @MatrixParam("path")
            @NotBlank
            @File
            String path
    ) throws Exception;
    
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String move(
            @FormParam("source")
            @NotBlank
            @File
            String source, 
            @MatrixParam("target")
            @NotBlank
            String target
    ) throws Exception;
}
