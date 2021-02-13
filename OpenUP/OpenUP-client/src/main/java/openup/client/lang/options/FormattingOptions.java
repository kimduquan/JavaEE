/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import java.util.HashMap;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class FormattingOptions extends HashMap<String, Object> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer tabSize;
    private Boolean insertSpaces;
    private Optional<Boolean> trimTrailingWhitespace;
    private Optional<Boolean> insertFinalNewline;
    private Optional<Boolean> trimFinalNewlines; 

    public Integer getTabSize() {
        return tabSize;
    }

    public void setTabSize(Integer tabSize) {
        this.tabSize = tabSize;
    }

    public Boolean getInsertSpaces() {
        return insertSpaces;
    }

    public void setInsertSpaces(Boolean insertSpaces) {
        this.insertSpaces = insertSpaces;
    }

    public Optional<Boolean> getTrimTrailingWhitespace() {
        return trimTrailingWhitespace;
    }

    public void setTrimTrailingWhitespace(Optional<Boolean> trimTrailingWhitespace) {
        this.trimTrailingWhitespace = trimTrailingWhitespace;
    }

    public Optional<Boolean> getInsertFinalNewline() {
        return insertFinalNewline;
    }

    public void setInsertFinalNewline(Optional<Boolean> insertFinalNewline) {
        this.insertFinalNewline = insertFinalNewline;
    }

    public Optional<Boolean> getTrimFinalNewlines() {
        return trimFinalNewlines;
    }

    public void setTrimFinalNewlines(Optional<Boolean> trimFinalNewlines) {
        this.trimFinalNewlines = trimFinalNewlines;
    }
}
