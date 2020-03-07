/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 *
 * @author FOXCONN
 */
public interface OpenUPLocalHome extends EJBLocalHome {
    
    openup.OpenUPLocal findByPrimaryKey(java.lang.Long key) throws FinderException;
    
    openup.OpenUPLocal create(java.lang.Long key) throws CreateException;
    
}
