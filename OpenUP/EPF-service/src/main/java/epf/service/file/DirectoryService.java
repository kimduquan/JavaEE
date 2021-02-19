/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import openup.schema.Role;

/**
 *
 * @author FOXCONN
 */
@javax.ws.rs.Path("file/directory")
@RolesAllowed(Role.ANY_ROLE)
@RequestScoped
public class DirectoryService implements epf.client.file.Directories {
    
    File buildDirectory(String dir) throws Exception{
        return new File(dir);
    }
    
    @Override
    public String createDirectories(
            String dir, 
            Map<String, String> attrs
    ) throws Exception {
        File directory = new File(dir);
        return Files.createDirectories(directory.toPath()).toString();
    }

    @Override
    public String createTempDirectory(
            String dir,
            String prefix, 
            Map<String, String> attrs
    ) throws Exception {
        if(dir != null && !dir.isEmpty()){
            File directory = new File(dir);
            return Files.createTempDirectory(directory.toPath(), prefix).toString();
        }
        return Files.createTempDirectory(prefix).toString();
    }

    @Override
    public List<String> list(
            String dir
    ) throws Exception {
        File directory = buildDirectory(dir);
        return Files.list(directory.toPath())
                .map(Path::toString)
                .collect(Collectors.toList());
    }
}
