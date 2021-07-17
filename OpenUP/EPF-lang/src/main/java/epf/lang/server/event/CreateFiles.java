/**
 * 
 */
package epf.lang.server.event;

import org.eclipse.lsp4j.CreateFilesParams;
import org.eclipse.lsp4j.WorkspaceEdit;
import epf.lang.util.Call;

/**
 * @author PC
 *
 */
public class CreateFiles extends Call<CreateFilesParams, WorkspaceEdit> {

	/**
	 * @param params
	 */
	public CreateFiles(final CreateFilesParams params) {
		super(params);
	}

	@Override
	public void run() {
		
	}
}
