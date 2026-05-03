package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The gRPC Call enables communication with external systems via the gRPC protocol, enabling efficient and reliable communication between distributed components.")
public class gRPCCall {
	
	public class Service {
		
		@NotNull
		@JsonPropertyDescription("The name of the GRPC service to call.")
		private String name;
		
		@NotNull
		@JsonPropertyDescription("The hostname of the GRPC service to call.")
		private String host;
		
		@JsonPropertyDescription("The port number of the GRPC service to call.")
		private Integer port;
		
		@JsonPropertyDescription("The authentication policy, or the name of the authentication policy, to use when calling the GRPC service.")
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
	@JsonPropertyDescription("The proto resource that describes the GRPC service to call.")
	private ExternalResource proto;
	
	@NotNull
	private Service service;
	
	@NotNull
	@JsonPropertyDescription("The name of the GRPC service method to call.")
	private String method;
	
	@JsonPropertyDescription("A name/value mapping of the method call's arguments, if any.")
	private Map<?, ?> arguments;

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

	public Map<?, ?> getArguments() {
		return arguments;
	}

	public void setArguments(Map<?, ?> arguments) {
		this.arguments = arguments;
	}
}
