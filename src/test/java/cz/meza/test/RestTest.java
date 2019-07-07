package cz.meza.test;

import cz.meza.microservice.rest.controller.TestAPI;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.test.spi.annotation.TestScoped;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.arquillian.CreateSwarm;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;

/**
 *
 */
@RunWith(Arquillian.class)
public class RestTest {

/*    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.options().port(7070));*/

    @ArquillianResource
    private URI uri;

    @Deployment
    public static WebArchive deployment() {
        File []  deps = Maven.resolver().loadPomFromFile("pom.xml").importDependencies(ScopeType.COMPILE, ScopeType.RUNTIME)
                .resolve().withoutTransitivity().asFile();
        WebArchive wrap = ShrinkWrap.create(WebArchive.class, "demo.war")
        .addPackages(true, "cz.meza")
        .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addAsLibraries(deps).addAsManifestResource("META-INF/microprofile-config.properties", "microprofile-config.properties");
        return  wrap;
    }


    @CreateSwarm
    public static Swarm createSwar() throws Exception {
        Swarm swarm = new Swarm();
        return  swarm.withProfile("default");
    }

    @Test
    @Ignore
    @RunAsClient
    public void test() {
/*
        wireMockRule.stubFor(get(urlMatching("/api/aloha")).willReturn(aResponse().withStatus(200).withBody("TEST")));
*/

        String body = given().get("/test/test").then().extract().asString();


        System.out.println("Archive" + body);

    }

    @Test
    @Ignore
    @RunAsClient
    public void testAPI() {
        Client client = ClientBuilder.newClient();
        System.out.println("Call");
        WebTarget target = client.target("http://localhost:8080/test");
        ResteasyWebTarget rTarget = (ResteasyWebTarget) target;
        TestAPI testAPI = ((ResteasyWebTarget) target).proxy(TestAPI.class);
        List<String> result = testAPI.testX();
        System.out.println("End call");

    }




}
