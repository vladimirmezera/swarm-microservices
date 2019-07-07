package cz.meza.microservice.rest.controller;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 *
 */
public interface TestAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public List<String> testX();
}
