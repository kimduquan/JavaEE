/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.guidance.templates;

import openup.embeddable.TestData;
import openup.embeddable.TestStep;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author FOXCONN
 */
@Entity
public class TestScript implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestScript)) {
            return false;
        }
        TestScript other = (TestScript) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.TestScript[ id=" + id + " ]";
    }
    
    private String TestName;

    /**
     * Get the value of TestName
     *
     * @return the value of TestName
     */
    public String getTestName() {
        return TestName;
    }

    /**
     * Set the value of TestName
     *
     * @param TestName new value of TestName
     */
    public void setTestName(String TestName) {
        this.TestName = TestName;
    }

    @Temporal(TemporalType.DATE)
    private Date Date;

    /**
     * Get the value of Date
     *
     * @return the value of Date
     */
    public Date getDate() {
        return Date;
    }

    /**
     * Set the value of Date
     *
     * @param Date new value of Date
     */
    public void setDate(Date Date) {
        this.Date = Date;
    }

    private String UseCaseTested;

    /**
     * Get the value of UseCaseTested
     *
     * @return the value of UseCaseTested
     */
    public String getUseCaseTested() {
        return UseCaseTested;
    }

    /**
     * Set the value of UseCaseTested
     *
     * @param UseCaseTested new value of UseCaseTested
     */
    public void setUseCaseTested(String UseCaseTested) {
        this.UseCaseTested = UseCaseTested;
    }

    private String TestDescription;

    /**
     * Get the value of TestDescription
     *
     * @return the value of TestDescription
     */
    public String getTestDescription() {
        return TestDescription;
    }

    /**
     * Set the value of TestDescription
     *
     * @param TestDescription new value of TestDescription
     */
    public void setTestDescription(String TestDescription) {
        this.TestDescription = TestDescription;
    }

    private String PreConditions;

    /**
     * Get the value of PreConditions
     *
     * @return the value of PreConditions
     */
    public String getPreConditions() {
        return PreConditions;
    }

    /**
     * Set the value of PreConditions
     *
     * @param PreConditions new value of PreConditions
     */
    public void setPreConditions(String PreConditions) {
        this.PreConditions = PreConditions;
    }

    private String PostConditions;

    /**
     * Get the value of PostConditions
     *
     * @return the value of PostConditions
     */
    public String getPostConditions() {
        return PostConditions;
    }

    /**
     * Set the value of PostConditions
     *
     * @param PostConditions new value of PostConditions
     */
    public void setPostConditions(String PostConditions) {
        this.PostConditions = PostConditions;
    }

    private String Notes;

    /**
     * Get the value of Notes
     *
     * @return the value of Notes
     */
    public String getNotes() {
        return Notes;
    }

    /**
     * Set the value of Notes
     *
     * @param Notes new value of Notes
     */
    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

    private String Result;

    /**
     * Get the value of Result
     *
     * @return the value of Result
     */
    public String getResult() {
        return Result;
    }

    /**
     * Set the value of Result
     *
     * @param Result new value of Result
     */
    public void setResult(String Result) {
        this.Result = Result;
    }

    @ElementCollection
    private List<TestStep> Test;

    /**
     * Get the value of TestStep
     *
     * @return the value of TestStep
     */
    public List<TestStep> getTest() {
        return Test;
    }

    /**
     * Set the value of TestStep
     *
     * @param Test new value of TestStep
     */
    public void setTest(List<TestStep> Test) {
        this.Test = Test;
    }

    @Embedded
    private TestData TestDataTable;

    /**
     * Get the value of TestDataTable
     *
     * @return the value of TestDataTable
     */
    public TestData getTestDataTable() {
        return TestDataTable;
    }

    /**
     * Set the value of TestDataTable
     *
     * @param TestDataTable new value of TestDataTable
     */
    public void setTestDataTable(TestData TestDataTable) {
        this.TestDataTable = TestDataTable;
    }

}
