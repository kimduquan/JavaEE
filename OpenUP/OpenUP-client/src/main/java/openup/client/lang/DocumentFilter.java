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
public class DocumentFilter {
    private Optional<String> language;
    private Optional<String> scheme;
    private Optional<String> pattern;

    public Optional<String> getLanguage() {
        return language;
    }

    public void setLanguage(Optional<String> language) {
        this.language = language;
    }

    public Optional<String> getScheme() {
        return scheme;
    }

    public void setScheme(Optional<String> scheme) {
        this.scheme = scheme;
    }

    public Optional<String> getPattern() {
        return pattern;
    }

    public void setPattern(Optional<String> pattern) {
        this.pattern = pattern;
    }
}
