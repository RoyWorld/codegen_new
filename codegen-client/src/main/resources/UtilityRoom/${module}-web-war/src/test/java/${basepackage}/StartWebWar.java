package ${basepackage};


import org.mortbay.jetty.Server;

import java.net.InetAddress;

/**
 * 使用Jetty运行调试Web应用, 在Console输入回车停止服务.
 */
public class StartWebWar {

    public static final int PORT = 8887;
    public static final String CONTEXT = "";
    public static final String BASE_URL = "http://localhost:8887";


    public static void main(String[] args) throws Exception {
        // System.setProperty("configFile", "/Users/xjli/managerui-biz-startup/up-war/src/main/resources/config/main-conf.properties");
        Server server = JettyFactory.buildNormalServer(PORT, CONTEXT);
        server.start();
        System.out.println("Server running at " + BASE_URL);
        System.out.println("Hit Enter in console to stop server");
        if (System.in.read() != 0) {
            server.stop();
            System.out.println("Server stopped");
        }
    }

}
