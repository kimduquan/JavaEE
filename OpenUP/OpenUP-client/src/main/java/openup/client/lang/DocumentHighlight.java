/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.kind.DocumentHighlightKind;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class DocumentHighlight {
    private Range range;
    private Optional<DocumentHighlightKind> kind;

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Optional<DocumentHighlightKind> getKind() {
        return kind;
    }

    public void setKind(Optional<DocumentHighlightKind> kind) {
        this.kind = kind;
    }
}
