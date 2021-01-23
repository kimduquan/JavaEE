/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class SemanticTokens {
    private Optional<String> resultId;
    private Integer[] data;

    public Optional<String> getResultId() {
        return resultId;
    }

    public void setResultId(Optional<String> resultId) {
        this.resultId = resultId;
    }

    public Integer[] getData() {
        return data;
    }

    public void setData(Integer[] data) {
        this.data = data;
    }
}
