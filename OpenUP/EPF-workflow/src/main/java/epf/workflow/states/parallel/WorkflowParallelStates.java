package epf.workflow.states.parallel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import epf.workflow.client.Internal;
import epf.workflow.model.WorkflowData;
import epf.workflow.schema.state.ParallelStateBranch;
import epf.workflow.states.WorkflowStates;
import epf.workflow.util.LinkBuilder;
import epf.workflow.util.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.ws.rs.core.Link;

@ApplicationScoped
public class WorkflowParallelStates {
	
	@Inject
	transient WorkflowStates workflowStates;
	
	public ResponseBuilder branches(final ResponseBuilder response, final String workflow, final String version, final String state, final List<ParallelStateBranch> branches, final WorkflowData workflowData) {
		final JsonArrayBuilder branchesArray = Json.createArrayBuilder();
		final List<Link> branchLinks = new ArrayList<>();
		final LinkBuilder builder = new LinkBuilder();
		int index = 0;
		final Iterator<ParallelStateBranch> branchIt = branches.iterator();
		while(branchIt.hasNext()) {
			final Link branchLink = Internal.branchLink(workflow, Optional.ofNullable(version), state, index);
			final Link link = builder.link(branchLink).at(response.getSize()).build();
			branchLinks.add(link);
			branchesArray.add(workflowData.getInput());
			branchIt.next();
			index++;
		}
		return workflowStates.partial(response, branchesArray.build(), branchLinks.toArray(new Link[0]));
	}
}
