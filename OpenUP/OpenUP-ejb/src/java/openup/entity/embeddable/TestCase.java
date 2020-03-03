/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity.embeddable;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class TestCase {
    
    private String TestCaseID;

    /**
     * Get the value of TestCaseID
     *
     * @return the value of TestCaseID
     */
    public String getTestCaseID() {
        return TestCaseID;
    }

    /**
     * Set the value of TestCaseID
     *
     * @param TestCaseID new value of TestCaseID
     */
    public void setTestCaseID(String TestCaseID) {
        this.TestCaseID = TestCaseID;
    }

    private String TestCaseName;

    /**
     * Get the value of TestCaseName
     *
     * @return the value of TestCaseName
     */
    public String getTestCaseName() {
        return TestCaseName;
    }

    /**
     * Set the value of TestCaseName
     *
     * @param TestCaseName new value of TestCaseName
     */
    public void setTestCaseName(String TestCaseName) {
        this.TestCaseName = TestCaseName;
    }

    private String Description;

    /**
     * Get the value of Description
     *
     * @return the value of Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Set the value of Description
     *
     * @param Description new value of Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    @ElementCollection
    private List<String> PreConditions;

    /**
     * Get the value of PreConditions
     *
     * @return the value of PreConditions
     */
    public List<String> getPreConditions() {
        return PreConditions;
    }

    /**
     * Set the value of PreConditions
     *
     * @param PreConditions new value of PreConditions
     */
    public void setPreConditions(List<String> PreConditions) {
        this.PreConditions = PreConditions;
    }

    @ElementCollection
    private List<String> PostConditions;

    /**
     * Get the value of PostConditions
     *
     * @return the value of PostConditions
     */
    public List<String> getPostConditions() {
        return PostConditions;
    }

    /**
     * Set the value of PostConditions
     *
     * @param PostConditions new value of PostConditions
     */
    public void setPostConditions(List<String> PostConditions) {
        this.PostConditions = PostConditions;
    }

    private String DataRequired;

    /**
     * Get the value of DataRequired
     *
     * @return the value of DataRequired
     */
    public String getDataRequired() {
        return DataRequired;
    }

    /**
     * Set the value of DataRequired
     *
     * @param DataRequired new value of DataRequired
     */
    public void setDataRequired(String DataRequired) {
        this.DataRequired = DataRequired;
    }

}
