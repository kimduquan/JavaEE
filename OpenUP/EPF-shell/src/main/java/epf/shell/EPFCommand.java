/**
 * 
 */
package epf.shell;

import epf.shell.cache.Cache;
import epf.shell.file.FileStore;
import epf.shell.image.Image;
import epf.shell.persistence.Persistence;
import epf.shell.rules.Rules;
import epf.shell.schema.Schema;
import epf.shell.security.Security;
import epf.shell.util.Utility;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;
import javax.enterprise.context.ApplicationScoped;

/**
 * @author PC
 *
 */
@TopCommand
@Command(name = "epf", subcommands = {Security.class, Persistence.class, Schema.class, FileStore.class, Rules.class, Utility.class, Image.class, Cache.class})
@ApplicationScoped
public class EPFCommand {
	
}
