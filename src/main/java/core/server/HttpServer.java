
package core.server;

import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import core.app.Router;

public class HttpServer {
    private static Server server;

    public static void start(TlsEngine tls, int port) throws Exception {
        server = new Server(port);

        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        try {
            sslContextFactory.setKeyStore(tls.getKeyStore());
            sslContextFactory.setKeyStorePassword("changeit");
            sslContextFactory.setKeyManagerPassword("changeit");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // HTTPS Configuration
        HttpConfiguration httpsConfig = new HttpConfiguration();

        // HTTP/2 Connection Factory
        HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(httpsConfig);
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();

        // SSL Connection Factory
        SslConnectionFactory sslcf = new SslConnectionFactory(sslContextFactory, alpn.getProtocol());

        // HTTP/2 Connector
        ServerConnector http2Connector = new ServerConnector(server, sslcf, alpn, h2, new HttpConnectionFactory(httpsConfig));
        http2Connector.setPort(port);

        server.setConnectors(new Connector[] {http2Connector});

        ResourceHandler staticCtx = new ResourceHandler();
        staticCtx.setDirectoriesListed(true);
        staticCtx.setResourceBase("ClientApp/dist");

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        //server.setHandler(contexts);

        // Configure context "/" (root) for servlets
        ServletContextHandler root = new ServletContextHandler(contexts, "/",
        ServletContextHandler.SESSIONS);

        var ctrls = Router.GetAllControllers();
        ctrls.forEach(ctxPath -> {
            root.addServlet(new ServletHolder(new AsyncControllerServlet(ctxPath)), ctxPath);
        });

        HandlerCollection handlers = new HandlerCollection();
        HandlerList list = new HandlerList();
        list.setHandlers(new Handler[] { staticCtx });
        handlers.setHandlers(new Handler[] { list, contexts, new DefaultHandler() });

        server.setHandler(handlers);
        server.setDumpAfterStart(false);
        server.dumpStdErr();
        server.start();
        server.join();
    }
}
