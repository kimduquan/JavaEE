package epf.net;

import java.io.InputStream;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import epf.net.schema.URL;
import epf.util.StringUtil;
import epf.util.json.JsonUtil;
import epf.function.net.ShortenUrlFunction;
import epf.function.persistence.PersistFunction;
import epf.hateoas.function.LinkFunction;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.NET)
@ApplicationScoped
public class Net implements epf.client.net.Net {

	@Override
	public Response rewriteUrl(final java.net.URL rawUrl) throws Exception {
		final URL url = new URL();
		url.setAuthority(rawUrl.getAuthority());
		url.setDefaultPort(rawUrl.getDefaultPort());
		url.setFile(rawUrl.getFile());
		url.setHost(rawUrl.getHost());
		url.setPath(rawUrl.getPath());
		url.setPort(rawUrl.getPort());
		url.setProtocol(rawUrl.getProtocol());
		url.setQuery(rawUrl.getQuery());
		url.setRef(rawUrl.getRef());
		url.setString(rawUrl.toString());
		url.setUserInfo(rawUrl.getUserInfo());
		
		final PersistFunction persistFunc = new PersistFunction();
		persistFunc.setSchema(epf.net.schema.Net.SCHEMA);
		persistFunc.setEntity(epf.net.schema.Net.URL);
		final ShortenUrlFunction shortenFunc = new ShortenUrlFunction();
		return Response.ok(url).links(LinkFunction.toLinks(persistFunc, shortenFunc)).build();
	}

	@Override
	public String shortenUrl(final InputStream body) throws Exception {
		final URL url = JsonUtil.fromJson(URL.class, body);
		return StringUtil.toShortString(url.getId());
	}
}
