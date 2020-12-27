/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.event;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.event.TreeDragDropEvent;

/**
 *
 * @author FOXCONN
 */
@ViewScoped
public class Event implements Serializable {
    
    public void onNodeSelect(NodeSelectEvent event) {
    }
    
    public void onNodeExpand(NodeExpandEvent event) {
    }
    
    public void onNodeCollapse(NodeCollapseEvent event) {
    }
    
    public void onNodeUnselect(NodeUnselectEvent event){
    }
    
    public void onTreeDragDrop(TreeDragDropEvent event){
    }
    
    public void onTabChange(TabChangeEvent event){
    }
    
    public void onTabClose(TabCloseEvent event){
    }
}
