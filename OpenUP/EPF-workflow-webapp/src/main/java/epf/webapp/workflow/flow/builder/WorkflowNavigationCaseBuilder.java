package epf.webapp.workflow.flow.builder;

import javax.el.ValueExpression;
import javax.faces.application.NavigationCase;
import javax.faces.flow.builder.NavigationCaseBuilder;

/**
 * @author PC
 *
 */
public class WorkflowNavigationCaseBuilder extends NavigationCaseBuilder {
	
	/**
	 * 
	 */
	private WorkflowRedirectBuilder redirect;
	
	/**
	 * 
	 */
	private NavigationCase navigationCase;

	@Override
	public NavigationCaseBuilder fromViewId(final String fromViewId) {
		return this;
	}

	@Override
	public NavigationCaseBuilder fromAction(final String fromAction) {
		return this;
	}

	@Override
	public NavigationCaseBuilder fromOutcome(final String fromOutcome) {
		return this;
	}

	@Override
	public NavigationCaseBuilder toViewId(final String toViewId) {
		return this;
	}

	@Override
	public NavigationCaseBuilder toFlowDocumentId(final String toFlowDocumentId) {
		return this;
	}

	@Override
	public NavigationCaseBuilder condition(final String condition) {
		return this;
	}

	@Override
	public NavigationCaseBuilder condition(final ValueExpression condition) {
		return this;
	}

	@Override
	public RedirectBuilder redirect() {
		return redirect;
	}
	
	/**
	 * @return
	 */
	public NavigationCase build() {
		return navigationCase;
	}

	/**
	 * @author PC
	 *
	 */
	public class WorkflowRedirectBuilder extends RedirectBuilder {

		@Override
		public RedirectBuilder parameter(final String name, final String value) {
			return this;
		}

		@Override
		public RedirectBuilder includeViewParams() {
			return this;
		}
	}
}
