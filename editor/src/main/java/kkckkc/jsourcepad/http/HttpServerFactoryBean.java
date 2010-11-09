package kkckkc.jsourcepad.http;

import com.sun.net.httpserver.HttpServer;
import kkckkc.jsourcepad.util.Config;
import org.springframework.beans.factory.FactoryBean;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpServerFactoryBean implements FactoryBean<HttpServer> {

	@Override
    public HttpServer getObject() throws Exception {
		InetSocketAddress addr = new InetSocketAddress(InetAddress.getByName(Config.getLocalhost()), Config.getHttpPort());

        System.out.println("addr = " + addr);

		HttpServer server = HttpServer.create(addr, 0);
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();

        System.out.println("HttpServerFactoryBean.getObject");

		return server;
    }

	@Override
    public Class<?> getObjectType() {
	    return HttpServer.class;
    }

	@Override
    public boolean isSingleton() {
	    return true;
    }

}
