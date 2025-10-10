package epf.query.cache;

import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import epf.util.logging.LogManager;
import io.quarkus.redis.datasource.codecs.Codec;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@ApplicationScoped
public class EntityCodec implements Codec {
	
	private static final Logger LOGGER = LogManager.getLogger(EntityCodec.class.getName());

	@Override
	public boolean canHandle(final Type clazz) {
		return clazz.getTypeName().equals(CacheEntry.class.getTypeName());
	}

	@Override
	public byte[] encode(final Object item) {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.toJson(item).getBytes();
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "encode", e);
			return null;
		}
	}

	@Override
	public Object decode(final byte[] item) {
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(new String(item), CacheEntry.class);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "decode", e);
			return null;
		}
	}

}
