package epf.webapp.workflow.internal;

import javax.faces.flow.ViewNode;

/**
 * @author PC
 *
 */
public class WorkflowViewNode extends ViewNode {
	
	/**
	 * 
	 */
	private String vdlDocumentId;
	
	/**
	 * 
	 */
	private String id;

	@Override
	public String getVdlDocumentId() {
		return vdlDocumentId;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setVdlDocumentId(final String vdlDocumentId) {
		this.vdlDocumentId = vdlDocumentId;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
