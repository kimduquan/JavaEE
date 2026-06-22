package epf.mcp.gateway;

import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.mcp.gateway.schema.EditResult;
import epf.mcp.gateway.schema.FindResult;
import epf.mcp.gateway.schema.ListResult;
import epf.mcp.gateway.schema.Prompts;
import epf.mcp.gateway.schema.ReadResult;
import epf.mcp.gateway.schema.SearchResult;
import epf.mcp.gateway.schema.ToolDescriptions;
import epf.mcp.gateway.schema.WriteResult;
import epf.naming.Naming;
import epf.persistence.schema.EntityType;
import io.quarkiverse.mcp.server.McpServer;
import io.quarkiverse.mcp.server.Prompt;
import io.quarkiverse.mcp.server.PromptArg;
import io.quarkiverse.mcp.server.PromptMessage;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Authenticated
public class Gateway {
	
	@Inject
	JsonWebToken jwt;

	@RestClient
	transient SchemaClient schemaClient;
	
	@McpServer(Naming.GATEWAY)
	@Tool(name = "list_files", description = ToolDescriptions.LIST_FILES_TOOL_DESCRIPTION, structuredContent = true)
	@RunOnVirtualThread
	ListResult listFiles(
			@ToolArg(name = "path", description = "Absolute path to the directory to list. Must be absolute, not relative.")
			final String path
			) {
		final ListResult result = new ListResult();
		return result;
	}
	
	@McpServer(Naming.GATEWAY)
	@Tool(name = "read_file", description = ToolDescriptions.READ_FILE_TOOL_DESCRIPTION, structuredContent = true)
	@RunOnVirtualThread
	ReadResult readFile(
			@ToolArg(name = "file_path", description = "Absolute path to the file to read. Must start with '/'.")
			final String file_path,
			@ToolArg(name = "offset", description = "Line number to start reading from (0-indexed). Default: 0.", required = false, defaultValue = "0")
			final Integer offset,
			@ToolArg(name = "limit", description = "Maximum number of lines to read. Default: 2000.", required = false, defaultValue = "2000")
			final Integer limit
			) {
		final ReadResult result = new ReadResult();
		return result;
	}
	
	@McpServer(Naming.GATEWAY)
	@Tool(name = "write_file", description = ToolDescriptions.WRITE_FILE_TOOL_DESCRIPTION, structuredContent = true)
	@RunOnVirtualThread
	WriteResult writeFile(
			@ToolArg(name = "file_path", description = "Absolute path where the file should be created. Must be absolute, not relative.")
			final String file_path,
			@ToolArg(name = "content", description = "The text content to write to the file. This parameter is required.")
			final String content
			) {
		final WriteResult result = new WriteResult();
		return result;
	}
	
	@McpServer(Naming.GATEWAY)
	@Tool(name = "edit_file", description = ToolDescriptions.EDIT_FILE_TOOL_DESCRIPTION, structuredContent = true)
	@RunOnVirtualThread
	EditResult editFile(
			@ToolArg(name = "file_path", description = "Absolute path to the file to edit. Must be absolute, not relative.")
			final String file_path,
			@ToolArg(name = "old_string", description = "The exact text to find and replace. Must be unique in the file unless replace_all is True.")
			final String old_string,
			@ToolArg(name = "new_string", description = "The text to replace old_string with. Must be different from old_string.")
			final String new_string,
			@ToolArg(name = "replace_all", description = "If True, replace all occurrences of old_string. If False (default), old_string must be unique.", required = false, defaultValue = "false")
			final Boolean replace_all
			) {
		final EditResult result = new EditResult();
		return result;
	}
	
	@McpServer(Naming.GATEWAY)
	@Tool(name = "find_files", description = ToolDescriptions.FIND_FILES_TOOL_DESCRIPTION, structuredContent = true)
	@RunOnVirtualThread
	FindResult findFiles(
			@ToolArg(name = "pattern", description = "Glob pattern to match files (e.g., '**/*.py', '*.txt', '/subdir/**/*.md').")
			final String pattern,
			@ToolArg(name = "path", description = "Base directory to search from. Defaults to the backend's default root.", required = false, defaultValue = "")
			final String path
			) {
		final FindResult result = new FindResult();
		return result;
	}
	
	@McpServer(Naming.GATEWAY)
	@Tool(name = "search_files", description = ToolDescriptions.SEARCH_FILES_TOOL_DESCRIPTION, structuredContent = true)
	@RunOnVirtualThread
	SearchResult searchFiles(
			@ToolArg(name = "pattern", description = "Glob pattern to match files (e.g., '**/*.py', '*.txt', '/subdir/**/*.md').")
			final String pattern,
			@ToolArg(name = "path", description = "Base directory to search from. Defaults to the backend's default root.", required = false, defaultValue = "")
			final String path,
			@ToolArg(name = "glob", description = "Glob pattern to filter which files to search (e.g., '*.py').")
			final String glob,
			@ToolArg(name = "output_mode", description = "Output format: 'files_with_matches' (file paths only, default), 'content' (matching lines with context), 'count' (match counts per file).", required = false, defaultValue = "files_with_matches")
			final String output_mode
			) {
		final SearchResult result = new SearchResult();
		return result;
	}
	
	@Prompt(name = Naming.GATEWAY)
	@RunOnVirtualThread
	PromptMessage getGatewayPrompt() throws Exception {
		final StringBuilder prompt = new StringBuilder();
		prompt.append(Prompts.AGENT_PROMPT);
		return PromptMessage.withAssistantRole(prompt.toString());
	}
	
	//@Prompt(name = Naming.PERSISTENCE, description = "create, update, delete data in database")
	//@RunOnVirtualThread
	PromptMessage getPersistencePrompt(@PromptArg(name = "schema_name") final String schemaName) throws Exception {
		final StringBuilder prompt = new StringBuilder();
		final SchemaBuilder schemaBuilder = new SchemaBuilder();
		final String authorization = "Bearer " + jwt.getRawToken();
		final List<EntityType> entities = schemaClient.getEntities(authorization).stream().filter(entity -> entity.getTable().getSchema().equals(schemaName)).collect(Collectors.toList());
		schemaBuilder.entities(entities);
		prompt.append("""
		Given below Jakarta Persistence API entity classes :
				""");
		prompt.append(schemaBuilder.build());
		return PromptMessage.withAssistantRole(prompt.toString());
	}

	//@Prompt(name = Naming.QUERY, description = "read data in database")
	//@RunOnVirtualThread
	PromptMessage getQueryPrompt(@PromptArg(name = "schema_name") final String schemaName) throws Exception {
		final StringBuilder prompt = new StringBuilder();
		final SchemaBuilder schemaBuilder = new SchemaBuilder();
		final String authorization = "Bearer " + jwt.getRawToken();
		final List<EntityType> entities = schemaClient.getEntities(authorization).stream().filter(entity -> entity.getTable().getSchema().equals(schemaName)).collect(Collectors.toList());
		schemaBuilder.entities(entities);
		prompt.append("""
		Given below Jakarta Persistence API entity classes :
				""");
		prompt.append(schemaBuilder.build());
		return PromptMessage.withAssistantRole(prompt.toString());
	}
	
	//@Prompt(name = "chrome_devtools", description = "browsing web content")
	//@RunOnVirtualThread
	PromptMessage get(@PromptArg(name = "url") final String url) throws Exception {
		final StringBuilder prompt = new StringBuilder();
		prompt.append("\n\nUse provided tools to answer user's question.");
		return PromptMessage.withAssistantRole(prompt.toString());
	}
}
