/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway.persistence;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Queries implements openup.api.persistence.Queries {

    @Override
    public Response getCriteriaQueryResult(List<PathSegment> paths, Integer firstResult, Integer maxResults) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response getNamedQueryResult(String name, Integer firstResult, Integer maxResults, UriInfo uriInfo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
