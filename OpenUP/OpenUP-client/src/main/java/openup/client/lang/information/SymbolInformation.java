/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.information;

import openup.client.lang.kind.SymbolKind;
import java.util.Optional;
import openup.client.lang.Location;
import openup.client.lang.SymbolTag;

/**
 *
 * @author FOXCONN
 */
public class SymbolInformation {
    private String name;
    private SymbolKind kind;
    private Optional<SymbolTag[]> tags;
    private Optional<Boolean> deprecated;
    private Location location;
    private Optional<String> containerName;

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

    public Optional<Boolean> getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Optional<Boolean> deprecated) {
        this.deprecated = deprecated;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Optional<String> getContainerName() {
        return containerName;
    }

    public void setContainerName(Optional<String> containerName) {
        this.containerName = containerName;
    }
}
