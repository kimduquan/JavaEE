/**
 * 
 */
package epf.shell;

import epf.shell.file.FileStore;
import epf.shell.image.Image;
import epf.shell.persistence.Persistence;
import epf.shell.rules.Rules;
import epf.shell.schema.Schema;
import epf.shell.security.Security;
import epf.shell.util.Utility;
import jakarta.enterprise.context.ApplicationScoped;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = "epf", subcommands = {Security.class, Persistence.class, Schema.class, FileStore.class, Rules.class, Utility.class, Image.class})
@ApplicationScoped
public class EPFCommand {
	
}
