/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.item;

import openup.client.lang.kind.SymbolKind;
import java.util.Optional;
import openup.client.lang.DocumentUri;
import openup.client.lang.Range;
import openup.client.lang.SymbolTag;

/**
 *
 * @author FOXCONN
 */
public class CallHierarchyItem {
    private String name;
    private SymbolKind kind;
    private Optional<SymbolTag[]> tags;
    private Optional<String> detail;
    private DocumentUri uri;
    private Range range;
    private Range selectionRange;
    private Optional data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SymbolKind getKind() {
        return kind;
    }

    public void setKind(SymbolKind kind) {
        this.kind = kind;
    }

    public Optional<SymbolTag[]> getTags() {
        return tags;
    }

    public void setTags(Optional<SymbolTag[]> tags) {
        this.tags = tags;
    }

    public Optional<String> getDetail() {
        return detail;
    }

    public void setDetail(Optional<String> detail) {
        this.detail = detail;
    }

    public DocumentUri getUri() {
        return uri;
    }

    public void setUri(DocumentUri uri) {
        this.uri = uri;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Range getSelectionRange() {
        return selectionRange;
    }

    public void setSelectionRange(Range selectionRange) {
        this.selectionRange = selectionRange;
    }

    public Optional getData() {
        return data;
    }

    public void setData(Optional data) {
        this.data = data;
    }
}
