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
public class LocationLink {
    private Optional<Range> originSelectionRange;
    private DocumentUri targetUri;
    private Range targetRange;
    private Range targetSelectionRange;

    public Optional<Range> getOriginSelectionRange() {
        return originSelectionRange;
    }

    public void setOriginSelectionRange(Optional<Range> originSelectionRange) {
        this.originSelectionRange = originSelectionRange;
    }

    public DocumentUri getTargetUri() {
        return targetUri;
    }

    public void setTargetUri(DocumentUri targetUri) {
        this.targetUri = targetUri;
    }

    public Range getTargetRange() {
        return targetRange;
    }

    public void setTargetRange(Range targetRange) {
        this.targetRange = targetRange;
    }

    public Range getTargetSelectionRange() {
        return targetSelectionRange;
    }

    public void setTargetSelectionRange(Range targetSelectionRange) {
        this.targetSelectionRange = targetSelectionRange;
    }
}
