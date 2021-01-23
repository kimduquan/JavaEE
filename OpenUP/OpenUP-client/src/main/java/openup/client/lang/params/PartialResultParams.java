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
public interface PartialResultParams {
    Optional<ProgressToken> getPartialResultToken();
    void setPartialResultToken(Optional<ProgressToken> partialResultToken);
}
