package cz.meza.test.app.service;

import cz.meza.test.app.bean.ApplicationCounterService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Only test simple REST service.
 * @author vladimir.mezera@gmail.com
 */
@Path("/dummy")
public class DummyRestServiceImpl {


    @Inject
    private ApplicationCounterService applicationCounterService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello() {
        return Response.ok("Hello word" + applicationCounterService.getActualCounter()).build();
    }
}
