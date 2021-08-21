/**
 * 
 */
package epf.shell.cache;

import epf.shell.Function;
import jakarta.enterprise.context.RequestScoped;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = "cache", subcommands = { Persistence.class })
@RequestScoped
@Function
public class Cache {
	
}
