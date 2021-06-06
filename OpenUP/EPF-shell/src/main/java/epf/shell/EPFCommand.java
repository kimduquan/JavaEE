/**
 * 
 */
package epf.shell;

import epf.shell.file.FileStore;
import epf.shell.persistence.Persistence;
import epf.shell.rules.Rules;
import epf.shell.security.Security;
import epf.shell.util.Utility;
import jakarta.enterprise.context.ApplicationScoped;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = "epf", subcommands = {Security.class, Persistence.class, FileStore.class, Rules.class, Utility.class})
@ApplicationScoped
public class EPFCommand {
	
}
