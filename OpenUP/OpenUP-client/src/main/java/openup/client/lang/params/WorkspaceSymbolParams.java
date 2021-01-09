/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;
import openup.client.lang.ProgressToken;

/**
 *
 * @author FOXCONN
 */
public class WorkspaceSymbolParams extends WorkDoneProgressParams implements PartialResultParams {
    private String query;

    @Override
    public Optional<ProgressToken> getPartialResultToken() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPartialResultToken(Optional<ProgressToken> partialResultToken) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
