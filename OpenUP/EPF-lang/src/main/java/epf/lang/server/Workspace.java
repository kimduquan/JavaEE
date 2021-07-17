/**
 * 
 */
package epf.lang.server;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.services.WorkspaceService;

/**
 * @author PC
 *
 */
public class Workspace implements WorkspaceService {

	@Override
	public void didChangeConfiguration(final DidChangeConfigurationParams params) {
		
	}

	@Override
	public void didChangeWatchedFiles(final DidChangeWatchedFilesParams params) {
		
	}

}
