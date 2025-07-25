package epf.workflow.task.call.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;

import epf.workflow.authentication.schema.Authentication;
import epf.workflow.schema.ExternalResource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Description("The gRPC Call enables communication with external systems via the gRPC protocol, enabling efficient and reliable communication between distributed components.")
public class gRPC {

	public class Service {
		@NotBlank
		@Description("The name of the GRPC service to call.")
		private String name;
		
		@NotBlank
		@Description("The hostname of the GRPC service to call.")
		private String host;
		
		@Description("The port number of the GRPC service to call.")
		private Integer port;
		
		@Description("The authentication policy, or the name of the authentication policy, to use when calling the GRPC service.")
		private Authentication authentication;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public Authentication getAuthentication() {
			return authentication;
		}

		public void setAuthentication(Authentication authentication) {
			this.authentication = authentication;
		}
	}
	
	@NotNull
	@Description("The proto resource that describes the GRPC service to call.")
	private ExternalResource proto;
	
	private Service service;
	
	@NotBlank
	@Description("The name of the GRPC service method to call.")
	private String method;
	
	@Description("A name/value mapping of the method call's arguments, if any.")
	private Map<String, Object> arguments;

	public ExternalResource getProto() {
		return proto;
	}

	public void setProto(ExternalResource proto) {
		this.proto = proto;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}
}
