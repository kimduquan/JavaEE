/**
 * 
 */
package epf.tests.file;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.ws.rs.core.Response;
import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.tests.client.ClientUtil;

/**
 * @author PC
 *
 */
public class FileUtil {
	
	public static String createFile(String token, Path file, Path path) throws Exception {
		URI fileUrl = GatewayUtil.get("file");
		try(Client client = ClientUtil.newClient(fileUrl)){
			client.authorization(token);
			try(InputStream input = Files.newInputStream(file)){
				try(Response response = epf.client.file.Files.createFile(client, input, path)){
					response.getStatus();
					return response.getLink("self").getTitle();
				}
			}
		}
	}

	public static void delete(String token, Path path) throws Exception {
		URI fileUrl = GatewayUtil.get("file");
		try(Client client = ClientUtil.newClient(fileUrl)){
			client.authorization(token);
			epf.client.file.Files.delete(client, path).getStatus();
		}
	}
}
