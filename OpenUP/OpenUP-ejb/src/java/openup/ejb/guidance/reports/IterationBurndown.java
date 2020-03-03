/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.guidance.reports;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.persistence.ElementCollection;
import openup.embeddable.Day;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class IterationBurndown {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private String Iteration;

    /**
     * Get the value of Iteration
     *
     * @return the value of Iteration
     */
    public String getIteration() {
        return Iteration;
    }

    /**
     * Set the value of Iteration
     *
     * @param Iteration new value of Iteration
     */
    public void setIteration(String Iteration) {
        this.Iteration = Iteration;
    }

    @ElementCollection
    private List<Day> Days;

    /**
     * Get the value of Days
     *
     * @return the value of Days
     */
    public List<Day> getDays() {
        return Days;
    }

    /**
     * Set the value of Days
     *
     * @param Days new value of Days
     */
    public void setDays(List<Day> Days) {
        this.Days = Days;
    }

}
