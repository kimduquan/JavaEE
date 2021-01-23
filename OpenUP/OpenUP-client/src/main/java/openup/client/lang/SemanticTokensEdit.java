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
public class SemanticTokensEdit {
    private Integer start;
    private Integer deleteCount;
    private Optional<Integer[]> data;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getDeleteCount() {
        return deleteCount;
    }

    public void setDeleteCount(Integer deleteCount) {
        this.deleteCount = deleteCount;
    }

    public Optional<Integer[]> getData() {
        return data;
    }

    public void setData(Optional<Integer[]> data) {
        this.data = data;
    }
}
