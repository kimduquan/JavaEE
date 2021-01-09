/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.net.URI;
import java.util.Optional;
import openup.client.lang.Range;

/**
 *
 * @author FOXCONN
 */
public class ShowDocumentParams {
    private URI uri;
    private Optional<Boolean> external;
    private Optional<Boolean> takeFocus;
    private Optional<Range> selection;

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Optional<Boolean> getExternal() {
        return external;
    }

    public void setExternal(Optional<Boolean> external) {
        this.external = external;
    }

    public Optional<Boolean> getTakeFocus() {
        return takeFocus;
    }

    public void setTakeFocus(Optional<Boolean> takeFocus) {
        this.takeFocus = takeFocus;
    }

    public Optional<Range> getSelection() {
        return selection;
    }

    public void setSelection(Optional<Range> selection) {
        this.selection = selection;
    }
}
