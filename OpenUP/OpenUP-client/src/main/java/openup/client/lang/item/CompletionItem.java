/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.item;

import openup.client.lang.kind.CompletionItemKind;
import java.util.Optional;
import openup.client.lang.Command;
import openup.client.lang.CompletionItemTag;
import openup.client.lang.InsertTextFormat;
import openup.client.lang.InsertTextMode;
import openup.client.lang.MarkupContent;
import openup.client.lang.TextEdit;

/**
 *
 * @author FOXCONN
 */
public class CompletionItem {
    private String label;
    private Optional<CompletionItemKind> kind;
    private CompletionItemTag[] tags;
    private Optional<String> detail;
    private MarkupContent documentation;
    @Deprecated
    private Optional<Boolean> deprecated;
    private Optional<Boolean> preselect;
    private Optional<String> sortText;
    private Optional<String> filterText;
    private Optional<String> insertText;
    private Optional<InsertTextFormat> insertTextFormat;
    private Optional<InsertTextMode> insertTextMode;
    private Optional<TextEdit> textEdit;
    private Optional<TextEdit[]> additionalTextEdits;
    private Optional<String[]> commitCharacters;
    private Optional<Command> command;
    private Optional data;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Optional<CompletionItemKind> getKind() {
        return kind;
    }

    public void setKind(Optional<CompletionItemKind> kind) {
        this.kind = kind;
    }

    public CompletionItemTag[] getTags() {
        return tags;
    }

    public void setTags(CompletionItemTag[] tags) {
        this.tags = tags;
    }

    public Optional<String> getDetail() {
        return detail;
    }

    public void setDetail(Optional<String> detail) {
        this.detail = detail;
    }

    public MarkupContent getDocumentation() {
        return documentation;
    }

    public void setDocumentation(MarkupContent documentation) {
        this.documentation = documentation;
    }

    public Optional<Boolean> getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Optional<Boolean> deprecated) {
        this.deprecated = deprecated;
    }

    public Optional<Boolean> getPreselect() {
        return preselect;
    }

    public void setPreselect(Optional<Boolean> preselect) {
        this.preselect = preselect;
    }

    public Optional<String> getSortText() {
        return sortText;
    }

    public void setSortText(Optional<String> sortText) {
        this.sortText = sortText;
    }

    public Optional<String> getFilterText() {
        return filterText;
    }

    public void setFilterText(Optional<String> filterText) {
        this.filterText = filterText;
    }

    public Optional<String> getInsertText() {
        return insertText;
    }

    public void setInsertText(Optional<String> insertText) {
        this.insertText = insertText;
    }

    public Optional<InsertTextFormat> getInsertTextFormat() {
        return insertTextFormat;
    }

    public void setInsertTextFormat(Optional<InsertTextFormat> insertTextFormat) {
        this.insertTextFormat = insertTextFormat;
    }

    public Optional<InsertTextMode> getInsertTextMode() {
        return insertTextMode;
    }

    public void setInsertTextMode(Optional<InsertTextMode> insertTextMode) {
        this.insertTextMode = insertTextMode;
    }

    public Optional<TextEdit> getTextEdit() {
        return textEdit;
    }

    public void setTextEdit(Optional<TextEdit> textEdit) {
        this.textEdit = textEdit;
    }

    public Optional<TextEdit[]> getAdditionalTextEdits() {
        return additionalTextEdits;
    }

    public void setAdditionalTextEdits(Optional<TextEdit[]> additionalTextEdits) {
        this.additionalTextEdits = additionalTextEdits;
    }

    public Optional<String[]> getCommitCharacters() {
        return commitCharacters;
    }

    public void setCommitCharacters(Optional<String[]> commitCharacters) {
        this.commitCharacters = commitCharacters;
    }

    public Optional<Command> getCommand() {
        return command;
    }

    public void setCommand(Optional<Command> command) {
        this.command = command;
    }

    public Optional getData() {
        return data;
    }

    public void setData(Optional data) {
        this.data = data;
    }
}
