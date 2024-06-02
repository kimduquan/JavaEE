package epf.lang;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.lang.internal.Ollama;
import epf.lang.schema.Message;
import epf.lang.schema.Request;
import epf.lang.schema.Response;
import epf.lang.schema.Role;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 * 
 */
@ServerEndpoint("/lang/{model}")
@ApplicationScoped
public class Lang {
	
	@RestClient
	Ollama ollama;

    @OnOpen
    public void onOpen(Session session, @PathParam(Naming.Lang.Internal.MODEL) String model) {
        System.out.println("onOpen> " + model);
    }

    @OnClose
    public void onClose(Session session, @PathParam(Naming.Lang.Internal.MODEL) String model) {
        System.out.println("onClose> " + model);
    }

    @OnError
    public void onError(Session session, @PathParam(Naming.Lang.Internal.MODEL) String model, Throwable throwable) {
        System.out.println("onError> " + model + ": " + throwable);
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(final Session session, @PathParam(Naming.Lang.Internal.MODEL) String model, String message) throws Exception {
        System.out.println("onMessage> " + model + ": " + message);
        final Request request = new Request();
        request.setModel(model);
        final Message requestMessage = new Message();
        requestMessage.setContent(message);
        requestMessage.setRole(Role.user);
        request.setMessages(Arrays.asList(requestMessage));
        final CompletionStage<InputStream> response = ollama.chat(request);
        response.handleAsync((stream, error) -> {
        	try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
        		try(Jsonb jsonb = JsonbBuilder.create()){
        			String line;
        		    while ((line = reader.readLine()) != null) {
        		        final Response res = jsonb.fromJson(line, Response.class);
        		        session.getBasicRemote().sendText(res.getMessage().getContent());
        		    }
        		}
    		}
        	catch(Exception ex) {
        		ex.printStackTrace();
        	}
        	return stream;
        });
    }
}
