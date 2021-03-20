/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import epf.schema.roles.Role;
import epf.service.ServiceException;

/**
 *
 * @author FOXCONN
 */
@javax.ws.rs.Path("file/directory")
@RolesAllowed(Role.DEFAULT_ROLE)
@RequestScoped
public class DirectoryService implements epf.client.file.Directories {
    
    @Override
    public String createDirectories(
    		final String dir, 
    		final Map<String, String> attrs
    ) {
    	final File directory = new File(dir);
        try {
			return Files.createDirectories(directory.toPath()).toString();
		} 
        catch (IOException e) {
			throw new ServiceException(e);
		}
    }

    @Override
    public String createTempDirectory(
    		final String dir,
    		final String prefix, 
    		final Map<String, String> attrs
    ) {
    	String result;
    	if(dir == null || dir.isEmpty()){
            try {
    			result = Files.createTempDirectory(prefix).toString();
    		} 
            catch (IOException e) {
            	throw new ServiceException(e);
    		}
        }
    	else {
    		final File directory = new File(dir);
            try {
				result = Files.createTempDirectory(directory.toPath(), prefix).toString();
			} 
            catch (IOException e) {
            	throw new ServiceException(e);
			}
    	}
        return result;
    }

    @Override
    public List<String> list(
    		final String dir
    ) {
        final File directory = new File(dir);
        try {
			return Files.list(directory.toPath())
			        .map(Path::toString)
			        .collect(Collectors.toList());
		} 
        catch (IOException e) {
        	throw new ServiceException(e);
		}
    }
}
