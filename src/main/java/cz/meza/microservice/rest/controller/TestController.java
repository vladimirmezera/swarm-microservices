package cz.meza.microservice.rest.controller;


import com.sun.jndi.toolkit.url.Uri;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.*;
import org.eclipse.microprofile.metrics.annotation.*;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.config.microprofile.config.ConfigSource;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.*;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.io.StringReader;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

@Path("/test")
@DeclareRoles({"king"})
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Inject
    private Config config;

    @Inject
    @ConfigProperty(name = "testPort",defaultValue = "7070")
    private Integer port;

    @Inject
    @Metric(name = "testCount")
    private Counter testCount;



    @Inject
    private MetricRegistry metricRegistry;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
/*
    @Timeout(value = 1, unit = ChronoUnit.SECONDS)
*/
    //@Retry(maxRetries = 3, retryOn = {RuntimeException.class})
  //  @Fallback(fallbackMethod = "fallback")

    @Metered(name = "king", absolute = true)
    @Timed(name="king_timed")
    @RolesAllowed({"king"})
    public List<String> getAll() throws InterruptedException {


        logger.error("Security context" + securityContext);
        Object jsonWebToken =  (Object) securityContext.getUserPrincipal();
        logger.error("Token context" + jsonWebToken);
      //  JsonWebToken
 /*       if (jsonWebToken == null) {
            logger.error("Token is null");
            //setVal("null");
        } else {
            logger.error("Username:" + jsonWebToken);
            //setVal(jsonWebToken.getName());
        }*/

        logger.error("Absolute path:" + "abs" + port);
        JsonObject jsonObject = Json.createObjectBuilder().build();
        JsonReader jsonReader = Json.createReader(new StringReader(("{\"test\":\"test\"}")));
        JsonReader jsonReader2 = Json.createReader(new StringReader(("{}")));

        Metadata metadata = new Metadata("matrix attendees", MetricType.HISTOGRAM);
        Histogram histogram = metricRegistry.histogram(metadata);
        histogram.update(testCount.getCount());
        histogram.update(1);
      //  Thread.sleep(2000);



        JsonObject jsonObject1 = jsonReader.readObject();
        JsonObject jsonObject2 = jsonReader2.readObject();
        JsonObjectBuilder builder =Json.createObjectBuilder();
        System.out.println(jsonObject2.isEmpty());
        System.out.println(jsonObject1.isEmpty());
        

        System.out.println("Port is " + port);
       // System.out.println("Config" + config.getPropertyNames());


        return new ArrayList<>();
    }

    //@Gauge(name = "valault", description = "testval", absolute = true, unit="PEAK")
    private String setVal(String val) {
        
        return val;
    }


    @Retry(delay = 1, delayUnit = ChronoUnit.MICROS, maxRetries = 2, retryOn = {RuntimeException.class})
    public void testRetry() {
        System.out.println("testRetrt");
        throw new RuntimeException("R");
    }


    public static List<String> fallback() {
        System.out.println("fallback");
        return null;
    }


}
