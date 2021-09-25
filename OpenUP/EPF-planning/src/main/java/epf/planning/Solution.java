/**
 * 
 */
package epf.planning;

import java.util.List;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;

import epf.delivery_processes.schema.DeliveryProcess;
import epf.planning.schema.Activity;
import epf.planning.schema.Artifact;
import epf.planning.schema.Iteration;
import epf.planning.schema.Milestone;
import epf.planning.schema.Phase;
import epf.planning.schema.Role;
import epf.planning.schema.Task;
import epf.roles.schema.RoleSet;
import epf.tasks.schema.Discipline;
import epf.work_products.schema.Domain;

/**
 * @author PC
 *
 */
@PlanningSolution
public class Solution {
	
	/**
	 * 
	 */
	@ProblemFactCollectionProperty
    @ValueRangeProvider(id = "Delivery_Processes")
	private List<DeliveryProcess> deliveryProcesses;
	
	/**
	 * 
	 */
	@ProblemFactCollectionProperty
    @ValueRangeProvider(id = "Roles")
	private List<RoleSet> roles;
	
	/**
	 * 
	 */
	@ProblemFactCollectionProperty
    @ValueRangeProvider(id = "Work_Products")
	private List<Domain> workProducts;
	
	/**
	 * 
	 */
	@ProblemFactCollectionProperty
    @ValueRangeProvider(id = "Tasks")
	private List<Discipline> tasks;
	
	/**
	 * 
	 */
	@PlanningEntityCollectionProperty
	private List<Activity> planningActivities;
	
	/**
	 * 
	 */
	@PlanningEntityCollectionProperty
	private List<Artifact> planningArtifacts;
	
	/**
	 * 
	 */
	@PlanningEntityCollectionProperty
	private List<epf.planning.schema.DeliveryProcess> planningDeliveryProcesses;
	
	/**
	 * 
	 */
	@PlanningEntityCollectionProperty
	private List<Iteration> planningIterations;
	
	/**
	 * 
	 */
	@PlanningEntityCollectionProperty
	private List<Milestone> planningMilestones;
	
	/**
	 * 
	 */
	@PlanningEntityCollectionProperty
	private List<Phase> planningPhases;
	
	/**
	 * 
	 */
	@PlanningEntityCollectionProperty
	private List<Role> planningRoles;
	
	/**
	 * 
	 */
	@PlanningEntityCollectionProperty
	private List<Task> planningTasks;

	/**
	 * @return the deliveryProcesses
	 */
	public List<DeliveryProcess> getDeliveryProcesses() {
		return deliveryProcesses;
	}

	/**
	 * @param deliveryProcesses the deliveryProcesses to set
	 */
	public void setDeliveryProcesses(final List<DeliveryProcess> deliveryProcesses) {
		this.deliveryProcesses = deliveryProcesses;
	}

	/**
	 * @return the roles
	 */
	public List<RoleSet> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(final List<RoleSet> roles) {
		this.roles = roles;
	}

	/**
	 * @return the workProducts
	 */
	public List<Domain> getWorkProducts() {
		return workProducts;
	}

	/**
	 * @param workProducts the workProducts to set
	 */
	public void setWorkProducts(final List<Domain> workProducts) {
		this.workProducts = workProducts;
	}

	/**
	 * @return the tasks
	 */
	public List<Discipline> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(final List<Discipline> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the planningActivities
	 */
	public List<Activity> getPlanningActivities() {
		return planningActivities;
	}

	/**
	 * @param planningActivities the planningActivities to set
	 */
	public void setPlanningActivities(final List<Activity> planningActivities) {
		this.planningActivities = planningActivities;
	}

	/**
	 * @return the planningArtifacts
	 */
	public List<Artifact> getPlanningArtifacts() {
		return planningArtifacts;
	}

	/**
	 * @param planningArtifacts the planningArtifacts to set
	 */
	public void setPlanningArtifacts(final List<Artifact> planningArtifacts) {
		this.planningArtifacts = planningArtifacts;
	}

	/**
	 * @return the planningDeliveryProcesses
	 */
	public List<epf.planning.schema.DeliveryProcess> getPlanningDeliveryProcesses() {
		return planningDeliveryProcesses;
	}

	/**
	 * @param planningDeliveryProcesses the planningDeliveryProcesses to set
	 */
	public void setPlanningDeliveryProcesses(final List<epf.planning.schema.DeliveryProcess> planningDeliveryProcesses) {
		this.planningDeliveryProcesses = planningDeliveryProcesses;
	}

	/**
	 * @return the planningIterations
	 */
	public List<Iteration> getPlanningIterations() {
		return planningIterations;
	}

	/**
	 * @param planningIterations the planningIterations to set
	 */
	public void setPlanningIterations(final List<Iteration> planningIterations) {
		this.planningIterations = planningIterations;
	}

	/**
	 * @return the planningMilestones
	 */
	public List<Milestone> getPlanningMilestones() {
		return planningMilestones;
	}

	/**
	 * @param planningMilestones the planningMilestones to set
	 */
	public void setPlanningMilestones(final List<Milestone> planningMilestones) {
		this.planningMilestones = planningMilestones;
	}

	/**
	 * @return the planningPhases
	 */
	public List<Phase> getPlanningPhases() {
		return planningPhases;
	}

	/**
	 * @param planningPhases the planningPhases to set
	 */
	public void setPlanningPhases(final List<Phase> planningPhases) {
		this.planningPhases = planningPhases;
	}

	/**
	 * @return the planningRoles
	 */
	public List<Role> getPlanningRoles() {
		return planningRoles;
	}

	/**
	 * @param planningRoles the planningRoles to set
	 */
	public void setPlanningRoles(final List<Role> planningRoles) {
		this.planningRoles = planningRoles;
	}

	/**
	 * @return the planningTasks
	 */
	public List<Task> getPlanningTasks() {
		return planningTasks;
	}

	/**
	 * @param planningTasks the planningTasks to set
	 */
	public void setPlanningTasks(final List<Task> planningTasks) {
		this.planningTasks = planningTasks;
	}
}
