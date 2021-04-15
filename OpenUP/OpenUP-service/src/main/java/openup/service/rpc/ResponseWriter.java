/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.rpc;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import openup.client.rpc.Message;
import openup.client.rpc.Response;

/**
 *
 * @author FOXCONN
 */
@Provider
@Produces(Message.APPLICATION_JSON_RPC)
public class ResponseWriter implements MessageBodyWriter<Response<?>> {

    @Override
    public boolean isWriteable(Class<?> cls, Type type, Annotation[] annotations, MediaType mediaType) {
        return cls.isAssignableFrom(Response.class) 
                && mediaType.equals(Message.APPLICATION_JSON_RPC_TYPE);
    }

    @Override
    public void writeTo(Response<?> msg, Class<?> cls, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> arg5, OutputStream output) throws IOException, WebApplicationException {
        final GenericType<Response<JsonObject>> resType = new GenericType<Response<JsonObject>>() {};
        try(Jsonb json = JsonbBuilder.create()){
            json.toJson(msg, resType.getType(), output);
        }
        catch(Exception ex){
            throw new WebApplicationException(ex);
        }
    }
    
}
