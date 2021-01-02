/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.file;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;

/**
 *
 * @author FOXCONN
 */
@Path("file/directory")
@RolesAllowed(epf.schema.openup.Role.ANY_ROLE)
@RequestScoped
public class Directories implements openup.client.file.Directories {
    
    File buildDirectory(String dir) throws Exception{
        return new File(dir);
    }
    
    @Override
    public String createDirectories(String dir, Map<String, String> attrs) throws Exception {
        File directory = new File(dir);
        return java.nio.file.Files.createDirectories(directory.toPath()).toString();
    }

    @Override
    public String createTempDirectory(String dir, String prefix, Map<String, String> attrs) throws Exception {
        if(dir != null && !dir.isEmpty()){
            File directory = new File(dir);
            return java.nio.file.Files.createTempDirectory(directory.toPath(), prefix).toString();
        }
        return java.nio.file.Files.createTempDirectory(prefix).toString();
    }

    @Override
    public List<String> list(String dir) throws Exception {
        File directory = buildDirectory(dir);
        return java.nio.file.Files.list(directory.toPath())
                .map(java.nio.file.Path::toString)
                .collect(Collectors.toList());
    }
}
