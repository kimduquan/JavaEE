/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.io.Serializable;

/**
 *
 * @author FOXCONN
 */
public class DestinationDefinition implements Serializable {
    public String name;
    public String interfaceName;
    public String resourceAdapter;
    public String destinationName;
}
