/**
 * 
 */
package epf.shell;

import epf.shell.persistence.Persistence;
import epf.shell.security.Security;
import jakarta.enterprise.context.ApplicationScoped;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = "epf", subcommands = {Security.class, Persistence.class})
@ApplicationScoped
public class EPFCommand {
	
}
