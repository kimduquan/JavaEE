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
import javax.persistence.Embedded;
import openup.embeddable.Iteration;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class ProjectBurndown {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @ElementCollection
    private List<Iteration> Actuals;

    /**
     * Get the value of Actuals
     *
     * @return the value of Actuals
     */
    public List<Iteration> getActuals() {
        return Actuals;
    }

    /**
     * Set the value of Actuals
     *
     * @param Actuals new value of Actuals
     */
    public void setActuals(List<Iteration> Actuals) {
        this.Actuals = Actuals;
    }

    @Embedded
    private Iteration Trend;

    /**
     * Get the value of Trend
     *
     * @return the value of Trend
     */
    public Iteration getTrend() {
        return Trend;
    }

    /**
     * Set the value of Trend
     *
     * @param Trend new value of Trend
     */
    public void setTrend(Iteration Trend) {
        this.Trend = Trend;
    }

}
