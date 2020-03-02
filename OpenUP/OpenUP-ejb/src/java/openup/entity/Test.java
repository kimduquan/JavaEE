/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author FOXCONN
 */
@Entity
public class Test implements Serializable {

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
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Test[ id=" + id + " ]";
    }
    
    private String TEST_STEP;

    /**
     * Get the value of TEST_STEP
     *
     * @return the value of TEST_STEP
     */
    public String getTEST_STEP() {
        return TEST_STEP;
    }

    /**
     * Set the value of TEST_STEP
     *
     * @param TEST_STEP new value of TEST_STEP
     */
    public void setTEST_STEP(String TEST_STEP) {
        this.TEST_STEP = TEST_STEP;
    }

    private String EXPECTED_TEST_RESULTS;

    /**
     * Get the value of EXPECTED_TEST_RESULTS
     *
     * @return the value of EXPECTED_TEST_RESULTS
     */
    public String getEXPECTED_TEST_RESULTS() {
        return EXPECTED_TEST_RESULTS;
    }

    /**
     * Set the value of EXPECTED_TEST_RESULTS
     *
     * @param EXPECTED_TEST_RESULTS new value of EXPECTED_TEST_RESULTS
     */
    public void setEXPECTED_TEST_RESULTS(String EXPECTED_TEST_RESULTS) {
        this.EXPECTED_TEST_RESULTS = EXPECTED_TEST_RESULTS;
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
