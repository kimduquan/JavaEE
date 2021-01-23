/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

/**
 *
 * @author FOXCONN
 */
public class FileEvent {
    private DocumentUri uri;
    private FileChangeType type;

    public DocumentUri getUri() {
        return uri;
    }

    public void setUri(DocumentUri uri) {
        this.uri = uri;
    }

    public FileChangeType getType() {
        return type;
    }

    public void setType(FileChangeType type) {
        this.type = type;
    }
}
