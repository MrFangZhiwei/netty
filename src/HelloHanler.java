import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title: netty
 * @Description:
 * @Company:www.keyonecn.com
 * @author:fzw
 * @date:2017/10/31 20:24
 * @version:1.0
 */
public class HelloHanler extends SimpleChannelHandler
{
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
    {
        System.out.println(e.getMessage());
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception
    {
        if (e instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) e;
            SimpleDateFormat format = new SimpleDateFormat("ss");
            System.out.println(event.getState()+format.format(new Date()));
            ChannelFuture write = ctx.getChannel().write("you will lose");
        }else {
            super.handleUpstream(ctx, e);
        }
    }
}