/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;

import epf.client.EPFException;
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
    
    /**
     * 
     */
    @Context
    private transient HttpServletRequest request;
    
    /**
     * @param path
     * @return
     */
    protected File buildFile(final String path){
        return new File(path);
    }

    /**
     *
     */
    @Override
    public long copy(
            final String target
    ) {
    	final File targetFile = buildFile(target);
    	final Var<Long> size = new Var<>(Long.valueOf(0));
    	final Var<IOException> error = new Var<>();
        try {
			request.getParts().forEach(part -> {
			    try {
					Files.copy(part.getInputStream(), targetFile.toPath());
			        size.set(s -> s + part.getSize());
				} 
			    catch (IOException e) {
			    	error.set(e);
				}
			});
		} 
        catch (IOException | ServletException e) {
        	throw new EPFException(e);
		}
        if(error.get() != null) {
        	throw new EPFException(error.get());
        }
        return size.get();
    }

    /**
     *
     */
    @Override
    public String createFile(
    		final String path, 
    		final Map<String, String> attrs
    ) {
    	final File file = new File(path);
        try {
			return Files.createFile(file.toPath()).toString();
		} 
        catch (IOException e) {
        	throw new EPFException(e);
		}
    }

    /**
     *
     */
    @Override
    public String createTempFile(
    		final String dir, 
    		final String prefix, 
    		final String suffix, 
    		final Map<String, String> attrs
    ) {
    	String result;
        if(dir == null || dir.isEmpty()){
            try {
    			result = Files.createTempFile(prefix, suffix).toString();
    		} 
            catch (IOException e) {
            	throw new EPFException(e);
    		}
        }
        else {
        	final File directory = new File(dir);
            try {
				result = Files.createTempFile(directory.toPath(), prefix, suffix).toString();
			} 
            catch (IOException e) {
            	throw new EPFException(e);
			}
        }
        return result;
    }

    /**
     *
     */
    @Override
    public void delete(
    		final String path
    ) {
    	final File file = buildFile(path);
        try {
			Files.delete(file.toPath());
		} 
        catch (IOException e) {
        	throw new EPFException(e);
		}
    }

    /**
     *
     */
    @Override
    public List<String> find(
    		final String start,
    		final Integer maxDepth
    ) {
    	final File startDir = buildFile(start);
        try {
			return Files.find(
			        startDir.toPath(), 
			        maxDepth, null
			)
			.map(Path::toString)
			.collect(Collectors.toList());
		} 
        catch (IOException e) {
        	throw new EPFException(e);
		}
    }

    /**
     *
     */
    @Override
    public StreamingOutput lines(
    		final String path
    ) {
    	final File file = buildFile(path);
        try {
			return new EntityOutput(Files.newInputStream(file.toPath()));
		} 
        catch (IOException e) {
        	throw new EPFException(e);
		}
    }

    /**
     *
     */
    @Override
    public String move(
    		final String source,
    		final String target
    ) {
        final File sourceFile = buildFile(source);
        final File targetFile = new File(target);
        try {
			return Files.move(sourceFile.toPath(), targetFile.toPath()).toString();
		} 
        catch (IOException e) {
        	throw new EPFException(e);
		}
    }
    
}
