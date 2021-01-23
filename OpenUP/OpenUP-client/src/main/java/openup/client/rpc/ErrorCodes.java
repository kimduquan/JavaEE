/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.rpc;

/**
 *
 * @author FOXCONN
 */
public interface ErrorCodes {
    public static final int ParseError = -32700;
    public static final int InvalidRequest = -32600;
    public static final int MethodNotFound = -32601;
    public static final int InvalidParams = -32602;
    public static final int InternalError = -32603;
    
    public static final int jsonrpcReservedErrorRangeStart = -32099;
    public static final int jsonrpcReservedErrorRangeEnd  = -32000;
    
    @Deprecated
    public static final int serverErrorStart = jsonrpcReservedErrorRangeStart;
    @Deprecated
    public static final int serverErrorEnd = jsonrpcReservedErrorRangeEnd;
    
    public static final int ServerNotInitialized = -32002;
    public static final int UnknownErrorCode = -32001;
    
    public static final int lspReservedErrorRangeStart = -32899;
    public static final int lspReservedErrorRangeEnd = -32800;
    
    public static final int ContentModified = -32801;
    public static final int RequestCancelled = -32800;
}
