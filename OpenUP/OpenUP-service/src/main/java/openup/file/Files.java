/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.file;

import java.io.File;
import java.util.List;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author FOXCONN
 */
@Path("file")
@RolesAllowed(epf.schema.openup.Role.ANY_ROLE)
@RequestScoped
public class Files implements openup.client.file.Files {
    
    @Context
    private HttpServletRequest request;
    
    File buildFile(String path) throws Exception{
        return new File(path);
    }

    @Override
    public long copy(String target) throws Exception {
        File targetFile = buildFile(target);
        long size = 0;
        for(Part part : request.getParts()){
            java.nio.file.Files.copy(part.getInputStream(), targetFile.toPath());
            size += part.getSize();
        }
        return size;
    }

    @Override
    public String createFile(String path, Map<String, String> attrs) throws Exception {
        File file = new File(path);
        return java.nio.file.Files.createFile(file.toPath()).toString();
    }

    @Override
    public String createTempFile(String dir, String prefix, String suffix, Map<String, String> attrs) throws Exception {
        if(dir != null && !dir.isEmpty()){
            File directory = new File(dir);
            return java.nio.file.Files.createTempFile(directory.toPath(), prefix, suffix).toString();
        }
        return java.nio.file.Files.createTempFile(prefix, suffix).toString();
    }

    @Override
    public void delete(String path) throws Exception {
        File file = buildFile(path);
        java.nio.file.Files.delete(file.toPath());
    }

    @Override
    public List<String> find(String start, Integer maxDepth) throws Exception {
        File startDir = buildFile(start);
        return java.nio.file.Files.find(
                startDir.toPath(), 
                maxDepth, null
        )
        .map(Path::toString)
        .toList();
    }

    @Override
    public StreamingOutput lines(String path) throws Exception {
        File file = buildFile(path);
        return new LinesOutput(java.nio.file.Files.lines(file.toPath()));
    }

    @Override
    public String move(String source, String target) throws Exception {
        File sourceFile = buildFile(source);
        File targetFile = new File(target);
        return java.nio.file.Files.move(sourceFile.toPath(), targetFile.toPath()).toString();
    }
    
}
