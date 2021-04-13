package io.kimmking.rpcfx.demo.provider.iniaialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.Data;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/2/25 16:43
 */
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {

    static final byte SPACE = (byte)' ';

    @Override
    protected void initChannel(Channel ch) throws Exception {

    }

    //内部类 ,cmd POJO
    @Data
    public static final class Cmd{

        private final ByteBuf name;
        private final ByteBuf args;

        public Cmd(ByteBuf name , ByteBuf args){
            this.name = name;
            this.args = args;
        }
    }

    //解码器
    public static final class CmdDecoder extends LineBasedFrameDecoder{

        public CmdDecoder(int maxLength){
            super(maxLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {

            ByteBuf frame = (ByteBuf)super.decode(ctx, buffer);
            if(frame == null){
                return null;
            }
            int index = frame.indexOf(frame.readerIndex(),frame.writerIndex(),SPACE);
            return new Cmd(frame.slice(frame.readerIndex(),index),frame.slice(index+1,frame.writerIndex()));
        }
    }


    // Cmd类型消息的拦截器
    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Cmd msg) throws Exception {

        }

    }

}
