/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;
import openup.client.lang.TokenFormat;

/**
 *
 * @author FOXCONN
 */
public class SemanticTokensClientCapabilities {

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }

    public Request getRequests() {
        return requests;
    }

    public void setRequests(Request requests) {
        this.requests = requests;
    }

    public String[] getTokenTypes() {
        return tokenTypes;
    }

    public void setTokenTypes(String[] tokenTypes) {
        this.tokenTypes = tokenTypes;
    }

    public String[] getTokenModifiers() {
        return tokenModifiers;
    }

    public void setTokenModifiers(String[] tokenModifiers) {
        this.tokenModifiers = tokenModifiers;
    }

    public TokenFormat[] getFormats() {
        return formats;
    }

    public void setFormats(TokenFormat[] formats) {
        this.formats = formats;
    }

    public Optional<Boolean> getOverlappingTokenSupport() {
        return overlappingTokenSupport;
    }

    public void setOverlappingTokenSupport(Optional<Boolean> overlappingTokenSupport) {
        this.overlappingTokenSupport = overlappingTokenSupport;
    }

    public Optional<Boolean> getMultilineTokenSupport() {
        return multilineTokenSupport;
    }

    public void setMultilineTokenSupport(Optional<Boolean> multilineTokenSupport) {
        this.multilineTokenSupport = multilineTokenSupport;
    }
    public class Request {
        private Optional<Boolean> range;
        private Optional<Boolean> full;

        public Optional<Boolean> getRange() {
            return range;
        }

        public void setRange(Optional<Boolean> range) {
            this.range = range;
        }

        public Optional<Boolean> getFull() {
            return full;
        }

        public void setFull(Optional<Boolean> full) {
            this.full = full;
        }
    }
    
    private Optional<Boolean> dynamicRegistration;
    private Request requests;
    private String[] tokenTypes;
    private String[] tokenModifiers;
    private TokenFormat[] formats;
    private Optional<Boolean> overlappingTokenSupport;
    private Optional<Boolean> multilineTokenSupport;
}
