/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.kind.MonikerKind;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class Moniker {
    private String scheme;
    private String identifier;
    private UniquenessLevel unique;
    private Optional<MonikerKind> kind;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public UniquenessLevel getUnique() {
        return unique;
    }

    public void setUnique(UniquenessLevel unique) {
        this.unique = unique;
    }

    public Optional<MonikerKind> getKind() {
        return kind;
    }

    public void setKind(Optional<MonikerKind> kind) {
        this.kind = kind;
    }
}
