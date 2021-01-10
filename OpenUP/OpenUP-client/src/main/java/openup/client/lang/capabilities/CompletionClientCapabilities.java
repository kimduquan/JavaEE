/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;
import openup.client.lang.kind.CompletionItemKind;
import openup.client.lang.CompletionItemTag;
import openup.client.lang.InsertTextMode;
import openup.client.lang.kind.MarkupKind;
import openup.client.lang.util.Properties;
import openup.client.lang.util.ValueSet;

/**
 *
 * @author FOXCONN
 */
public class CompletionClientCapabilities {
    public class CompletionItem {
        private Optional<Boolean> snippetSupport;
        private Optional<Boolean> commitCharactersSupport;
        private Optional<MarkupKind[]> documentationFormat;
        private Optional<Boolean> deprecatedSupport;
        private Optional<Boolean> preselectSupport;
        private Optional<ValueSet<CompletionItemTag>> tagSupport;
        private Optional<Boolean> insertReplaceSupport;
        private Optional<Properties> resolveSupport;
        private Optional<ValueSet<InsertTextMode>> insertTextModeSupport;

        public Optional<Boolean> getSnippetSupport() {
            return snippetSupport;
        }

        public void setSnippetSupport(Optional<Boolean> snippetSupport) {
            this.snippetSupport = snippetSupport;
        }

        public Optional<Boolean> getCommitCharactersSupport() {
            return commitCharactersSupport;
        }

        public void setCommitCharactersSupport(Optional<Boolean> commitCharactersSupport) {
            this.commitCharactersSupport = commitCharactersSupport;
        }

        public Optional<MarkupKind[]> getDocumentationFormat() {
            return documentationFormat;
        }

        public void setDocumentationFormat(Optional<MarkupKind[]> documentationFormat) {
            this.documentationFormat = documentationFormat;
        }

        public Optional<Boolean> getDeprecatedSupport() {
            return deprecatedSupport;
        }

        public void setDeprecatedSupport(Optional<Boolean> deprecatedSupport) {
            this.deprecatedSupport = deprecatedSupport;
        }

        public Optional<Boolean> getPreselectSupport() {
            return preselectSupport;
        }

        public void setPreselectSupport(Optional<Boolean> preselectSupport) {
            this.preselectSupport = preselectSupport;
        }

        public Optional<ValueSet<CompletionItemTag>> getTagSupport() {
            return tagSupport;
        }

        public void setTagSupport(Optional<ValueSet<CompletionItemTag>> tagSupport) {
            this.tagSupport = tagSupport;
        }

        public Optional<Boolean> getInsertReplaceSupport() {
            return insertReplaceSupport;
        }

        public void setInsertReplaceSupport(Optional<Boolean> insertReplaceSupport) {
            this.insertReplaceSupport = insertReplaceSupport;
        }

        public Optional<Properties> getResolveSupport() {
            return resolveSupport;
        }

        public void setResolveSupport(Optional<Properties> resolveSupport) {
            this.resolveSupport = resolveSupport;
        }

        public Optional<ValueSet<InsertTextMode>> getInsertTextModeSupport() {
            return insertTextModeSupport;
        }

        public void setInsertTextModeSupport(Optional<ValueSet<InsertTextMode>> insertTextModeSupport) {
            this.insertTextModeSupport = insertTextModeSupport;
        }
    }
    Optional<Boolean> dynamicRegistration;
    Optional<CompletionItem> completionItem;
    Optional<ValueSet<CompletionItemKind>> completionItemKind;
    Optional<Boolean> contextSupport;
}
