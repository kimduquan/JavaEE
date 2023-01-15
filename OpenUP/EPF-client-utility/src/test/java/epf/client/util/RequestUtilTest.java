package epf.client.util;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

import org.junit.Test;
import org.mockito.Mockito;

public class RequestUtilTest {

	@Test
	public void testBuildMatrixParameters() {
		WebTarget webTarget = Mockito.mock(WebTarget.class);
		Mockito.when(webTarget.matrixParam(Mockito.any(), Mockito.any())).thenReturn(webTarget);
		PathSegment pathSegment = Mockito.mock(PathSegment.class);
		MultivaluedHashMap<String, String> params = new MultivaluedHashMap<>();
		params.putSingle("single", "single-value");
		params.putSingle("null", null);
		params.put("null2", null);
		params.putSingle("tenant", "tenant-1");
		Mockito.when(pathSegment.getMatrixParameters()).thenReturn(params);
		RequestUtil.buildMatrixParameters(webTarget, pathSegment);
		Mockito.verify(webTarget, Mockito.times(1)).matrixParam(Mockito.eq("single"), Mockito.any());
		Mockito.verify(webTarget, Mockito.times(1)).matrixParam(Mockito.eq("null"));
		Mockito.verify(webTarget, Mockito.times(1)).matrixParam(Mockito.eq("null2"));
		Mockito.verify(webTarget, Mockito.never()).matrixParam(Mockito.eq("tenant"), Mockito.any());
		Mockito.verify(webTarget, Mockito.times(3)).matrixParam(Mockito.any(), Mockito.any());
	}

	@Test
	public void testBuildQueryParameters() {
		WebTarget webTarget = Mockito.mock(WebTarget.class);
		Mockito.when(webTarget.queryParam(Mockito.any(), Mockito.any())).thenReturn(webTarget);
		UriInfo uriInfo = Mockito.mock(UriInfo.class);
		MultivaluedHashMap<String, String> params = new MultivaluedHashMap<>();
		params.putSingle("single", "single-value");
		params.putSingle("null", null);
		params.put("null2", null);
		Mockito.when(uriInfo.getQueryParameters()).thenReturn(params);
		RequestUtil.buildQueryParameters(webTarget, uriInfo);
		Mockito.verify(webTarget, Mockito.times(1)).queryParam(Mockito.eq("single"), Mockito.any());
		Mockito.verify(webTarget, Mockito.times(1)).queryParam(Mockito.eq("null"));
		Mockito.verify(webTarget, Mockito.times(1)).queryParam(Mockito.eq("null2"));
		Mockito.verify(webTarget, Mockito.times(3)).queryParam(Mockito.any(), Mockito.any());
	}

	@Test
	public void testBuildTarget() {
	}

	@Test
	public void testBuildHeaders() {
	}

	@Test
	public void testBuildInvoke() {
	}

	@Test
	public void testBuildRequest() {
	}

	@Test
	public void testBuildLinkRequest() {
	}

	@Test
	public void testBuildSource() {
	}

}
