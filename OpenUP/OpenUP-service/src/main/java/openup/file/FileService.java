/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.file;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;
import epf.schema.roles.Role;
import epf.util.Var;
import epf.util.client.EntityOutput;
import java.nio.file.Path;

/**
 *
 * @author FOXCONN
 */
@javax.ws.rs.Path("file")
@RolesAllowed(Role.DEFAULT_ROLE)
@RequestScoped
public class FileService implements epf.client.file.Files {
    
    @Context
    private HttpServletRequest request;
    
    File buildFile(String path) throws Exception{
        return new File(path);
    }

    @Override
    public long copy(
            String target
    ) throws Exception {
        File targetFile = buildFile(target);
        Var<Long> size = new Var<>(Long.valueOf(0));
        Var<Exception> ex = new Var<>();
        request.getParts().forEach(part -> {
            try {
				Files.copy(part.getInputStream(), targetFile.toPath());
	            size.set(s -> s + part.getSize());
			} 
            catch (Exception e) {
				ex.set(e);
			}
        });
        if(ex.get() != null) {
        	throw ex.get();
        }
        return size.get();
    }

    @Override
    public String createFile(
            String path, 
            Map<String, String> attrs
    ) throws Exception {
        File file = new File(path);
        return Files.createFile(file.toPath()).toString();
    }

    @Override
    public String createTempFile(
            String dir, 
            String prefix, 
            String suffix, 
            Map<String, String> attrs
    ) throws Exception {
        if(dir != null && !dir.isEmpty()){
            File directory = new File(dir);
            return Files.createTempFile(directory.toPath(), prefix, suffix).toString();
        }
        return Files.createTempFile(prefix, suffix).toString();
    }

    @Override
    public void delete(
            String path
    ) throws Exception {
        File file = buildFile(path);
        Files.delete(file.toPath());
    }

    @Override
    public List<String> find(
            String start,
            Integer maxDepth
    ) throws Exception {
        File startDir = buildFile(start);
        return Files.find(
                startDir.toPath(), 
                maxDepth, null
        )
        .map(Path::toString)
        .collect(Collectors.toList());
    }

    @Override
    public StreamingOutput lines(
            String path
    ) throws Exception {
        File file = buildFile(path);
        return new EntityOutput(Files.newInputStream(file.toPath()));
    }

    @Override
    public String move(
            String source,
            String target
    ) throws Exception {
        File sourceFile = buildFile(source);
        File targetFile = new File(target);
        return Files.move(sourceFile.toPath(), targetFile.toPath()).toString();
    }
    
}
