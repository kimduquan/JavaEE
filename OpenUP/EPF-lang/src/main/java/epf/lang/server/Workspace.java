/**
 * 
 */
package epf.lang.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.eclipse.lsp4j.CreateFilesParams;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.lang.server.event.CreateFiles;
import epf.lang.server.event.ExecuteCommand;

/**
 * @author PC
 *
 */
public class Workspace implements WorkspaceService {
	
	/**
	 * 
	 */
	private transient final List<String> files = Collections.synchronizedList(new ArrayList<>());

	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@Inject
	private transient Event<ExecuteCommand> execCmd;
	
	/**
	 * 
	 */
	@Inject
	private transient Event<FileEvent> changeWatchedFiles;
	
	/**
	 * 
	 */
	@Inject
	private transient Event<CreateFiles> createFiles;
	
	@Override
	public CompletableFuture<Object> executeCommand(final ExecuteCommandParams params) {
		return executor.supplyAsync(() -> new ExecuteCommand(params))
				.thenApply(call -> {
					execCmd.fire(call);
					return call;
					}
				)
				.thenApply(call -> call.getResult());
	}

	@Override
	public void didChangeConfiguration(final DidChangeConfigurationParams params) {
		
	}

	@Override
	public void didChangeWatchedFiles(final DidChangeWatchedFilesParams params) {
		params.getChanges().forEach(event -> changeWatchedFiles.fire(event));
	}
	
	@Override
	public void didChangeWorkspaceFolders(final DidChangeWorkspaceFoldersParams params) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CompletableFuture<WorkspaceEdit> willCreateFiles(final CreateFilesParams params) {
		return executor.supplyAsync(() -> new CreateFiles(params))
				.thenApply(call -> {
					createFiles.fire(call);
					return call;
					}
				)
				.thenApply(call -> call.getResult());
	}
	
	@Override
	public void didCreateFiles(CreateFilesParams params) {
		files.addAll(params.getFiles().stream().map(f -> f.getUri()).collect(Collectors.toList()));
	}
}
