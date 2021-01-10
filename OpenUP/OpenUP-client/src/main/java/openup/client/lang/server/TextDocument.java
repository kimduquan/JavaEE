/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.server;

import openup.client.lang.CallHierarchyIncomingCall;
import openup.client.lang.CallHierarchyOutgoingCall;
import openup.client.lang.CodeAction;
import openup.client.lang.CodeLens;
import openup.client.lang.ColorPresentation;
import openup.client.lang.CompletionList;
import openup.client.lang.DocumentHighlight;
import openup.client.lang.DocumentLink;
import openup.client.lang.DocumentSymbol;
import openup.client.lang.FoldingRange;
import openup.client.lang.Hover;
import openup.client.lang.LinkedEditingRanges;
import openup.client.lang.Location;
import openup.client.lang.LocationLink;
import openup.client.lang.Moniker;
import openup.client.lang.Range;
import openup.client.lang.SelectionRange;
import openup.client.lang.SemanticTokens;
import openup.client.lang.SemanticTokensDelta;
import openup.client.lang.SignatureHelp;
import openup.client.lang.TextEdit;
import openup.client.lang.WorkspaceEdit;
import openup.client.lang.information.ColorInformation;
import openup.client.lang.item.CallHierarchyItem;
import openup.client.lang.item.CompletionItem;
import openup.client.lang.params.CallHierarchyIncomingCallsParams;
import openup.client.lang.params.CallHierarchyOutgoingCallsParams;
import openup.client.lang.params.CallHierarchyPrepareParams;
import openup.client.lang.params.CodeActionParams;
import openup.client.lang.params.CodeLensParams;
import openup.client.lang.params.ColorPresentationParams;
import openup.client.lang.params.CompletionParams;
import openup.client.lang.params.DeclarationParams;
import openup.client.lang.params.DefinitionParams;
import openup.client.lang.params.DidChangeTextDocumentParams;
import openup.client.lang.params.DidCloseTextDocumentParams;
import openup.client.lang.params.DidOpenTextDocumentParams;
import openup.client.lang.params.DidSaveTextDocumentParams;
import openup.client.lang.params.DocumentColorParams;
import openup.client.lang.params.DocumentFormattingParams;
import openup.client.lang.params.DocumentHighlightParams;
import openup.client.lang.params.DocumentLinkParams;
import openup.client.lang.params.DocumentOnTypeFormattingParams;
import openup.client.lang.params.DocumentRangeFormattingParams;
import openup.client.lang.params.DocumentSymbolParams;
import openup.client.lang.params.FoldingRangeParams;
import openup.client.lang.params.HoverParams;
import openup.client.lang.params.ImplementationParams;
import openup.client.lang.params.LinkedEditingRangeParams;
import openup.client.lang.params.MonikerParams;
import openup.client.lang.params.PrepareRenameParams;
import openup.client.lang.params.PublishDiagnosticsParams;
import openup.client.lang.params.ReferenceParams;
import openup.client.lang.params.RenameParams;
import openup.client.lang.params.SelectionRangeParams;
import openup.client.lang.params.SemanticTokensDeltaParams;
import openup.client.lang.params.SemanticTokensParams;
import openup.client.lang.params.SemanticTokensRangeParams;
import openup.client.lang.params.SignatureHelpParams;
import openup.client.lang.params.TypeDefinitionParams;
import openup.client.lang.params.WillSaveTextDocumentParams;

/**
 *
 * @author FOXCONN
 */
public interface TextDocument extends openup.client.rpc.Server {
    void didOpen(DidOpenTextDocumentParams params);
    void didChange(DidChangeTextDocumentParams params);
    void willSave(WillSaveTextDocumentParams params);
    TextEdit[] willSaveWaitUntil(WillSaveTextDocumentParams params) throws Error;
    void didSave(DidSaveTextDocumentParams params);
    void didClose(DidCloseTextDocumentParams params);
    void publishDiagnostics(PublishDiagnosticsParams params);
    CompletionList completion(CompletionParams params) throws Error;
    CompletionItem resolve(CompletionItem params) throws Error;
    Hover hover(HoverParams params) throws Error;
    SignatureHelp signatureHelp(SignatureHelpParams params) throws Error;
    LocationLink[] declaration(DeclarationParams params) throws Error;
    LocationLink[] definition(DefinitionParams params) throws Error;
    LocationLink[] typeDefinition(TypeDefinitionParams params) throws Error;
    LocationLink[] implementation(ImplementationParams params) throws Error;
    Location[] references(ReferenceParams params) throws Error;
    DocumentHighlight[] documentHighlight(DocumentHighlightParams params) throws Error;
    DocumentSymbol[] documentSymbol(DocumentSymbolParams params) throws Error;
    CodeAction[] codeAction(CodeActionParams params) throws Error;
    CodeAction resolve(CodeAction params) throws Error;
    CodeLens[] codeLens(CodeLensParams params) throws Error;
    CodeLens resolve(CodeLens params) throws Error;
    Object refresh() throws Error;
    DocumentLink[] documentLink(DocumentLinkParams params) throws Error;
    DocumentLink resolve(DocumentLink params) throws Error;
    ColorInformation[] documentColor(DocumentColorParams params) throws Error;
    ColorPresentation[] colorPresentation(ColorPresentationParams params) throws Error;
    TextEdit[] formatting(DocumentFormattingParams params) throws Error;
    TextEdit[] rangeFormatting(DocumentRangeFormattingParams params) throws Error;
    TextEdit[] onTypeFormatting(DocumentOnTypeFormattingParams params) throws Error;
    WorkspaceEdit rename(RenameParams params) throws Error;
    Range prepareRename(PrepareRenameParams params) throws Error;
    FoldingRange[] foldingRange(FoldingRangeParams params) throws Error;
    SelectionRange[] selectionRange(SelectionRangeParams params) throws Error;
    CallHierarchyItem[] prepareCallHierarchy(CallHierarchyPrepareParams params) throws Error;
    CallHierarchyIncomingCall[] incomingCalls(CallHierarchyIncomingCallsParams params) throws Error;
    CallHierarchyOutgoingCall[] outgoingCalls(CallHierarchyOutgoingCallsParams params) throws Error;
    SemanticTokens full(SemanticTokensParams params) throws Error;
    SemanticTokensDelta delta(SemanticTokensDeltaParams params) throws Error;
    SemanticTokens range(SemanticTokensRangeParams params) throws Error;
    //Object refresh() throws Error;
    LinkedEditingRanges linkedEditingRange(LinkedEditingRangeParams params) throws Error;
    Moniker[] moniker(MonikerParams params) throws Error;
}
