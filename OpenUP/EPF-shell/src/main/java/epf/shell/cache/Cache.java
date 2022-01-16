/**
 * 
 */
package epf.shell.cache;

import epf.naming.Naming;
import epf.shell.Function;
import javax.enterprise.context.RequestScoped;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = Naming.CACHE, subcommands = { Persistence.class })
@RequestScoped
@Function
public class Cache {
	
}
