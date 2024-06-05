package epf.lang.internal;

import java.util.LinkedList;
import java.util.List;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import epf.lang.schema.Message;
import epf.lang.schema.Request;
import epf.lang.schema.Role;

/**
 * 
 */
public class EPFChatLanguageModel implements ChatLanguageModel {
	
	private final Ollama ollama;
	private final String model;
	
	/**
	 * @param ollama
	 * @param model
	 */
	public EPFChatLanguageModel(Ollama ollama, String model) {
		this.ollama = ollama;
		this.model = model;
	}

	@Override
	public Response<AiMessage> generate(final List<ChatMessage> messages) {
		final Request request = new Request();
		request.setModel(model);
		request.setStream(false);
		request.setMessages(new LinkedList<>());
		messages.forEach(chatMessage -> {
			final Message message = new Message();
			message.setRole(Role.user);
			switch(chatMessage.type()) {
				case AI:
					break;
				case SYSTEM:
					message.setRole(Role.system);
					break;
				case TOOL_EXECUTION_RESULT:
					break;
				case USER:
					message.setRole(Role.user);
					break;
				default:
					break;
			}
			message.setContent(chatMessage.text());
			request.getMessages().add(message);
		});
		return Response.from(AiMessage.from(ollama.chat(request).getResponse()));
	}

}
