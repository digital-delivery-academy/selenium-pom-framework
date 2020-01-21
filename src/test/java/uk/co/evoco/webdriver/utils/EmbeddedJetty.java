package uk.co.evoco.webdriver.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class EmbeddedJetty {

    private Server jettyServer;

    public EmbeddedJetty() {
        jettyServer = new Server(4442);
    }

    public void start() throws Exception {
        ResourceHandler resHandler = new ResourceHandler();
        resHandler.setBaseResource(Resource.newClassPathResource("/www"));
        jettyServer.setHandler(resHandler);
        jettyServer.start();
        Thread.sleep(10000);
    }

    public void stop() throws Exception {
        jettyServer.stop();
    }

    public int getPort() {
//        return ((ServerConnector) this.jettyServer.getConnectors()[0]).getLocalPort();
        return 4442;
    }
}
