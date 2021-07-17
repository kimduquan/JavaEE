/**
 * 
 */
package epf.lang.server;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.services.TextDocumentService;

/**
 * @author PC
 *
 */
public class TextDocument implements TextDocumentService {

	@Override
	public void didOpen(final DidOpenTextDocumentParams params) {
		
	}

	@Override
	public void didChange(final DidChangeTextDocumentParams params) {
		
	}

	@Override
	public void didClose(final DidCloseTextDocumentParams params) {
		
	}

	@Override
	public void didSave(final DidSaveTextDocumentParams params) {
		
	}

}
