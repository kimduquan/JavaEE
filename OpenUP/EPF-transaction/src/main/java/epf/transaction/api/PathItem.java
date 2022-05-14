package epf.transaction.api;

import java.util.List;
import java.util.Map;
import epf.transaction.api.parameter.Parameter;
import epf.transaction.api.server.Server;

/**
 * 
 */
public class PathItem extends Extensible {

	/**
	 *
	 */
	private String ref;
	
	enum HttpMethod {
        POST, 
        GET, 
        PUT, 
        PATCH, 
        DELETE, 
        HEAD, 
        OPTIONS, 
        TRACE
    };
    
    /**
     *
     */
    private String summary;
    
    /**
     *
     */
    private String description;
    
    /**
     *
     */
    private Operation GET;
    
    /**
     *
     */
    private Operation PUT;
    
    /**
     *
     */
    private Operation POST;
    
    /**
     *
     */
    private Operation DELETE;
    
    /**
     *
     */
    private Operation OPTIONS;
    
    /**
     *
     */
    private Operation HEAD;
    
    /**
     *
     */
    private Operation PATCH;
    
    /**
     *
     */
    private Operation TRACE;
    
    /**
     *
     */
    private Map<PathItem.HttpMethod, Operation> operations;
    
    /**
     *
     */
    private List<Server> servers;
    
    /**
     *
     */
    private List<Parameter> parameters;

	public String getRef() {
		return ref;
	}

	public void setRef(final String ref) {
		this.ref = ref;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Operation getGET() {
		return GET;
	}

	public void setGET(final Operation gET) {
		GET = gET;
	}

	public Operation getPUT() {
		return PUT;
	}

	public void setPUT(final Operation pUT) {
		PUT = pUT;
	}

	public Operation getPOST() {
		return POST;
	}

	public void setPOST(final Operation pOST) {
		POST = pOST;
	}

	public Operation getDELETE() {
		return DELETE;
	}

	public void setDELETE(final Operation dELETE) {
		DELETE = dELETE;
	}

	public Operation getOPTIONS() {
		return OPTIONS;
	}

	public void setOPTIONS(final Operation oPTIONS) {
		OPTIONS = oPTIONS;
	}

	public Operation getHEAD() {
		return HEAD;
	}

	public void setHEAD(final Operation hEAD) {
		HEAD = hEAD;
	}

	public Operation getPATCH() {
		return PATCH;
	}

	public void setPATCH(final Operation pATCH) {
		PATCH = pATCH;
	}

	public Operation getTRACE() {
		return TRACE;
	}

	public void setTRACE(final Operation tRACE) {
		TRACE = tRACE;
	}

	public Map<PathItem.HttpMethod, Operation> getOperations() {
		return operations;
	}

	public void setOperations(final Map<PathItem.HttpMethod, Operation> operations) {
		this.operations = operations;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(final List<Server> servers) {
		this.servers = servers;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(final List<Parameter> parameters) {
		this.parameters = parameters;
	}
}
