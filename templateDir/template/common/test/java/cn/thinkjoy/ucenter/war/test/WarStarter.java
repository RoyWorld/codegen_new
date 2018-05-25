package cn.thinkjoy.ucenter.war.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
* TODO 一句话描述该类用途
* <p/>
* 创建时间: 15/6/30 上午9:36<br/>
*
* @author qyang
* @since v0.0.1
*/
public class WarStarter {
    public static final String BASE_URL = "http://localhost:8099";

    public static void main(String[] args) {
        Server server = new Server(8099);
        WebAppContext context = new WebAppContext("src/main/webapp", "");
        //context.setWar("/Users/qyang/works/workspace/thinkjoy-dap/dap-mq-bridge-war/src/main/webapp");
//        context.setContextPath("/src/main/webapp");
//        context.setParentLoaderPriority(true);
        server.setHandler(context);

        try {
            server.start();
            System.out.println("Server running at " + BASE_URL);
            //server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
