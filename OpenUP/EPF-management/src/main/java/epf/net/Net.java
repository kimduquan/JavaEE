package epf.net;

import java.io.InputStream;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import epf.net.schema.URL;
import epf.util.StringUtil;
import epf.util.json.ext.JsonUtil;
import epf.function.net.ShortenUrlFunction;
import epf.function.persistence.PersistFunction;
import epf.function.LinkFunction;
import epf.naming.Naming;

@Path(Naming.NET)
@ApplicationScoped
public class Net {

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

	public String shortenUrl(final InputStream body) throws Exception {
		final URL url = JsonUtil.fromJson(body, URL.class);
		return StringUtil.toShortString(url.getId());
	}
}
