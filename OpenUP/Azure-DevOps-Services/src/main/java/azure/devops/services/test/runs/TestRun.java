package azure.devops.services.test.runs;

import azure.devops.services.test.CustomTestField;
import azure.devops.services.test.ShallowReference;

/**
 * @author PC
 * Test run details.
 */
public class TestRun {

	ShallowReference build;
	BuildConfiguration buildConfiguration;
	String comment;
	String completedDate;
	String controller;
	String createdDate;
	CustomTestField[] customFields;
	String dropLocation;
	ShallowReference dtlAutEnvironment;
	ShallowReference dtlEnvironment;
}
