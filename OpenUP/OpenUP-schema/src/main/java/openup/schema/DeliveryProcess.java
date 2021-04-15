/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.DELIVERY_PROCESS)
@Schema(name = OpenUP.DELIVERY_PROCESS, title = "Delivery Process")
@Entity(name = OpenUP.DELIVERY_PROCESS)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_DELIVERY_PROCESS")
public class DeliveryProcess {

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long processId;
    
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name = "DELIVERY_PROCESS")
    private epf.schema.delivery_processes.DeliveryProcess deliveryProcess;
    
    /**
     * 
     */
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;
    
    /**
     * 
     */
    @Column(name = "SUMMARY")
    private String summary;

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(final Long processId) {
        this.processId = processId;
    }

    public epf.schema.delivery_processes.DeliveryProcess getDeliveryProcess() {
        return deliveryProcess;
    }

    public void setDeliveryProcess(final epf.schema.delivery_processes.DeliveryProcess deliveryProcess) {
        this.deliveryProcess = deliveryProcess;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }
}
