/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.embeddable;

import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Iteration {
    
    private int Iteration;

    /**
     * Get the value of Iteration
     *
     * @return the value of Iteration
     */
    public int getIteration() {
        return Iteration;
    }

    /**
     * Set the value of Iteration
     *
     * @param Iteration new value of Iteration
     */
    public void setIteration(int Iteration) {
        this.Iteration = Iteration;
    }

    private int WorkItemsPointsRemaining;

    /**
     * Get the value of WorkItemsPointsRemaining
     *
     * @return the value of WorkItemsPointsRemaining
     */
    public int getWorkItemsPointsRemaining() {
        return WorkItemsPointsRemaining;
    }

    /**
     * Set the value of WorkItemsPointsRemaining
     *
     * @param WorkItemsPointsRemaining new value of WorkItemsPointsRemaining
     */
    public void setWorkItemsPointsRemaining(int WorkItemsPointsRemaining) {
        this.WorkItemsPointsRemaining = WorkItemsPointsRemaining;
    }

    private int Velocity;

    /**
     * Get the value of Velocity
     *
     * @return the value of Velocity
     */
    public int getVelocity() {
        return Velocity;
    }

    /**
     * Set the value of Velocity
     *
     * @param Velocity new value of Velocity
     */
    public void setVelocity(int Velocity) {
        this.Velocity = Velocity;
    }

}
