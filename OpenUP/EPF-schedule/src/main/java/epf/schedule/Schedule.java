package epf.schedule;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.enterprise.concurrent.ManagedScheduledExecutorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.schedule.internal.Messaging;
import epf.schedule.internal.ScheduledRequest;
import epf.schedule.internal.RecurringTimeIntervalTrigger;
import epf.schedule.schema.RecurringTimeInterval;
import epf.schedule.util.ScheduleUtil;
import epf.util.StringUtil;
import epf.util.UUIDUtil;

@ApplicationScoped
@Path(Naming.SCHEDULE)
public class Schedule implements epf.schedule.client.Schedule {
	
	private transient final Map<String, ScheduledFuture<Response>> scheduledFutures = new ConcurrentHashMap<>();

	@Resource(lookup = Naming.Schedule.EXECUTOR_SERVICE)
	private transient ManagedScheduledExecutorService scheduledExecutor;
	
	@Inject
	@Readiness
	private transient Registry registry;
	
	@Inject
	@Readiness
	private transient Messaging messaging;
	
	@PreDestroy
	protected void preDestroy() {
		scheduledFutures.values().parallelStream().forEach(scheduledFuture -> scheduledFuture.cancel(true));
		scheduledFutures.clear();
	}

	@Override
	public Response schedule(final UriInfo uriInfo, final HttpHeaders headers, final List<PathSegment> paths, final InputStream body) {
		final PathSegment schedulePath = uriInfo.getPathSegments().get(0);
		final String service = schedulePath.getMatrixParameters().getFirst("service");
		final String method = schedulePath.getMatrixParameters().getFirst("method");
		final String recurringTimeIntervalParam = schedulePath.getMatrixParameters().getFirst("recurringTimeInterval");
		if(!StringUtil.isEmpty(service) && !StringUtil.isEmpty(method) && !StringUtil.isEmpty(recurringTimeIntervalParam)) {
			final URI serviceUri = registry.lookup(service).orElse(null);
			final RecurringTimeInterval recurringTimeInterval = ScheduleUtil.parse(recurringTimeIntervalParam);
			if(serviceUri != null && recurringTimeInterval != null) {
				final UUID uuid = UUID.randomUUID();
				final ScheduledRequest call = new ScheduledRequest(uuid, Optional.empty());
				final ScheduledFuture<Response> response = scheduledExecutor.schedule(call, new RecurringTimeIntervalTrigger(recurringTimeInterval));
				scheduledFutures.put(uuid.toString(), response);
				return Response.ok(uuid.toString()).build();
			}
		}
		throw new BadRequestException();
	}

	@Override
	public Response cancel(final UriInfo uriInfo, final List<PathSegment> paths) {
		final PathSegment schedulePath = uriInfo.getPathSegments().get(0);
		final String identityName = schedulePath.getMatrixParameters().getFirst("identityName");
		if(identityName != null) {
			final UUID uuid = UUIDUtil.fromString(identityName);
			if(uuid != null) {
				final ScheduledFuture<Response> response = scheduledFutures.remove(identityName);
				if(response != null) {
					response.cancel(true);
					return Response.ok().build();
				}
				else {
					throw new NotFoundException();
				}
			}
		}
		throw new BadRequestException();
	}
}
