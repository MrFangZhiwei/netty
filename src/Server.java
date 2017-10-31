import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Title: netty
 * @Description:
 * @Company:www.keyonecn.com
 * @author:fzw
 * @date:2017/10/31 20:12
 * @version:1.0
 */
public class Server
{
    public static void main(String[] args)
    {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));
        HashedWheelTimer timer = new HashedWheelTimer();
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory()
        {
            @Override
            public ChannelPipeline getPipeline() throws Exception
            {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("idle",new IdleStateHandler(timer,5,5,10));
                pipeline.addLast("decoder",new StringDecoder());
                pipeline.addLast("encoder",new StringEncoder());
                pipeline.addLast("helloHanler",new HelloHanler());
                return pipeline;
            }
        });
        serverBootstrap.setOption("child.tcpNoDelay",true);
        serverBootstrap.setOption("child.keepAlive",true);
        serverBootstrap.bind(new InetSocketAddress(10101));
        System.out.println("start");
    }
}