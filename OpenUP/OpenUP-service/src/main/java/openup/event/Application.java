/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.event;

import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    private Map<String, Broadcaster> broadcasters;
}
