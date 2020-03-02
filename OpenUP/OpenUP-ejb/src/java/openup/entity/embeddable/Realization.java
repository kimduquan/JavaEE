/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity.embeddable;

import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Realization {
    
    private String ViewOfParticipants;

    /**
     * Get the value of ViewOfParticipants
     *
     * @return the value of ViewOfParticipants
     */
    public String getViewOfParticipants() {
        return ViewOfParticipants;
    }

    /**
     * Set the value of ViewOfParticipants
     *
     * @param ViewOfParticipants new value of ViewOfParticipants
     */
    public void setViewOfParticipants(String ViewOfParticipants) {
        this.ViewOfParticipants = ViewOfParticipants;
    }

    private String BasicScenario;

    /**
     * Get the value of BasicScenario
     *
     * @return the value of BasicScenario
     */
    public String getBasicScenario() {
        return BasicScenario;
    }

    /**
     * Set the value of BasicScenario
     *
     * @param BasicScenario new value of BasicScenario
     */
    public void setBasicScenario(String BasicScenario) {
        this.BasicScenario = BasicScenario;
    }

    private String AdditionalScenarios;

    /**
     * Get the value of AdditionalScenarios
     *
     * @return the value of AdditionalScenarios
     */
    public String getAdditionalScenarios() {
        return AdditionalScenarios;
    }

    /**
     * Set the value of AdditionalScenarios
     *
     * @param AdditionalScenarios new value of AdditionalScenarios
     */
    public void setAdditionalScenarios(String AdditionalScenarios) {
        this.AdditionalScenarios = AdditionalScenarios;
    }

}
