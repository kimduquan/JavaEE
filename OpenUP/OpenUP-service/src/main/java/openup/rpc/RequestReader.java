/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import openup.client.lang.params.CancelParams;
import openup.client.lang.params.ProgressParams;
import openup.client.rpc.Message;
import openup.client.rpc.Request;

/**
 *
 * @author FOXCONN
 */
@Provider
@Consumes(Message.APPLICATION_JSON_RPC)
public class RequestReader implements MessageBodyReader<Request> {

    @Override
    public boolean isReadable(Class<?> cls, Type type, Annotation[] annotations, MediaType mediaType) {
        return cls.isAssignableFrom(Request.class) 
                && mediaType.equals(Message.APPLICATION_JSON_RPC_TYPE);
    }

    @Override
    public Request readFrom(Class<Request> cls, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> arg4, InputStream input) throws IOException, WebApplicationException {
        final GenericType<Request<JsonObject>> rawType = new GenericType<Request<JsonObject>>() {};
        try(Jsonb json = JsonbBuilder.create()){
            Request<JsonObject> req = json.fromJson(input, rawType.getType());
            if(req.getMethod().equals("$/cancelRequest")){
                final GenericType<Request<CancelParams>> reqType = new GenericType<Request<CancelParams>>() {};
                Request<CancelParams> request = json.fromJson(input, reqType.getType());
                return request;
            }
            else if(req.getMethod().equals("$/progress")){
                final GenericType<Request<ProgressParams >> reqType = new GenericType<Request<ProgressParams >>() {};
                Request<ProgressParams > request = json.fromJson(input, reqType.getType());
                return request;
            }
            return req;
        }
        catch(Exception ex){
            throw new WebApplicationException(ex);
        }
    }
    
}
