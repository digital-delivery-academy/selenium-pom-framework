package uk.co.evoco.webdriver.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class EmbeddedJetty {

    private static final Logger logger = LogManager.getLogger(EmbeddedJetty.class);

    private Server jettyServer;

    public EmbeddedJetty() {
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
        jettyServer = new Server(getPort());
    }

    public void start() throws Exception {
        logger.info("Starting embedded jetty on port {} for integration test web app - jetty logs are disabled", getPort());
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
        return 4442;
    }

    /**
     * This is here for debugging the integration test app
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        EmbeddedJetty embeddedJetty = new EmbeddedJetty();
        embeddedJetty.start();
    }
}
