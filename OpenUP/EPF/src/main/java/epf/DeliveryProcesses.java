/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf;

import epf.schema.delivery_processes.DeliveryProcess;
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
@Type
@Schema(title = "Delivery Processes")
@Entity
@Table(name = "_DELIVERY_PROCESSES_", schema = "EPF")
public class DeliveryProcesses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "DELIVERY_PROCESS")
    private DeliveryProcess deliveryProcess;
    
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryProcess getDeliveryProcess() {
        return deliveryProcess;
    }

    public void setDeliveryProcess(DeliveryProcess deliveryProcess) {
        this.deliveryProcess = deliveryProcess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
