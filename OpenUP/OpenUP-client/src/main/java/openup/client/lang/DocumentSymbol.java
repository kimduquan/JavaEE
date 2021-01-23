/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.kind.SymbolKind;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class DocumentSymbol {
    private String name;
    private Optional<String> detail;
    private SymbolKind kind;
    private Optional<SymbolTag[]> tags;
    private Optional<Boolean> deprecated;
    private Range range;
    private Range selectionRange;
    private Optional<DocumentSymbol[]> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getDetail() {
        return detail;
    }

    public void setDetail(Optional<String> detail) {
        this.detail = detail;
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

    public Optional<Boolean> getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Optional<Boolean> deprecated) {
        this.deprecated = deprecated;
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

    public Optional<DocumentSymbol[]> getChildren() {
        return children;
    }

    public void setChildren(Optional<DocumentSymbol[]> children) {
        this.children = children;
    }
}
