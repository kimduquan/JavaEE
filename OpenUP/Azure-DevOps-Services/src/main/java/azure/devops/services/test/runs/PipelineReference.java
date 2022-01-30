package azure.devops.services.test.runs;

/**
 * @author PC
 * Pipeline reference
 */
public class PipelineReference {

	/**
	 * Reference of the job
	 */
	JobReference jobReference;
	/**
	 * Reference of the phase.
	 */
	PhaseReference phaseReference;
	/**
	 * Reference of the pipeline with which this pipeline instance is related.
	 */
	int pipelineId;
	/**
	 * Reference of the stage.
	 */
	StageReference stageReference;
	
	public JobReference getJobReference() {
		return jobReference;
	}
	public void setJobReference(JobReference jobReference) {
		this.jobReference = jobReference;
	}
	public PhaseReference getPhaseReference() {
		return phaseReference;
	}
	public void setPhaseReference(PhaseReference phaseReference) {
		this.phaseReference = phaseReference;
	}
	public int getPipelineId() {
		return pipelineId;
	}
	public void setPipelineId(int pipelineId) {
		this.pipelineId = pipelineId;
	}
	public StageReference getStageReference() {
		return stageReference;
	}
	public void setStageReference(StageReference stageReference) {
		this.stageReference = stageReference;
	}
}
