package openup.architecture.schema;

/**
 * @author PC
 *
 */
public class Architecture_Notebook {

	/**
	 * 
	 */
	private Technical_Architecture fulfilledSlots;
	
	/**
	 * 
	 */
	private String responsible;
	
	/**
	 * 
	 */
	private String modifiedBy;

	public Technical_Architecture getFulfilledSlots() {
		return fulfilledSlots;
	}

	public void setFulfilledSlots(final Technical_Architecture fulfilledSlots) {
		this.fulfilledSlots = fulfilledSlots;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(final String responsible) {
		this.responsible = responsible;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
