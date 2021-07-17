/**
 * 
 */
package epf.lang.server;

import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.eclipse.microprofile.context.ManagedExecutor;

/**
 * @author PC
 *
 */
public class Server implements LanguageServer, LanguageClientAware {
	
	/**
	 * 
	 */
	private transient final WorkspaceService workspace;
	
	/**
	 * 
	 */
	private transient final TextDocumentService document;
	
	/**
	 * 
	 */
	private transient final ManagedExecutor executor;
	
	/**
	 * 
	 */
	private transient LanguageClient client;
	
	/**
	 * @param workspace
	 * @param document
	 * @param executor
	 */
	@Inject
	public Server(final WorkspaceService workspace, final TextDocumentService document, final ManagedExecutor executor) {
		this.workspace = workspace;
		this.document = document;
		this.executor = executor;
	}

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		return executor.supplyAsync(InitializeResult::new);
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		return executor.supplyAsync(Object::new);
	}

	@Override
	public void exit() {
	}

	@Override
	public TextDocumentService getTextDocumentService() {
		return this.document;
	}

	@Override
	public WorkspaceService getWorkspaceService() {
		return this.workspace;
	}

	@Override
	public void connect(final LanguageClient client) {
		this.client = client;
	}

	/**
	 * @return the client
	 */
	protected LanguageClient getClient() {
		return client;
	}

}
