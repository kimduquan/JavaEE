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
public class TestStep {

    private String TestStep;

    /**
     * Get the value of TestStep
     *
     * @return the value of TestStep
     */
    public String getTestStep() {
        return TestStep;
    }

    /**
     * Set the value of TestStep
     *
     * @param TestStep new value of TestStep
     */
    public void setTestStep(String TestStep) {
        this.TestStep = TestStep;
    }

        private String ExpectedTestResults;

    /**
     * Get the value of ExpectedTestResults
     *
     * @return the value of ExpectedTestResults
     */
    public String getExpectedTestResults() {
        return ExpectedTestResults;
    }

    /**
     * Set the value of ExpectedTestResults
     *
     * @param ExpectedTestResults new value of ExpectedTestResults
     */
    public void setExpectedTestResults(String ExpectedTestResults) {
        this.ExpectedTestResults = ExpectedTestResults;
    }

    private boolean P;

    /**
     * Get the value of P
     *
     * @return the value of P
     */
    public boolean isP() {
        return P;
    }

    /**
     * Set the value of P
     *
     * @param P new value of P
     */
    public void setP(boolean P) {
        this.P = P;
    }

    private boolean F;

    /**
     * Get the value of F
     *
     * @return the value of F
     */
    public boolean isF() {
        return F;
    }

    /**
     * Set the value of F
     *
     * @param F new value of F
     */
    public void setF(boolean F) {
        this.F = F;
    }

}
