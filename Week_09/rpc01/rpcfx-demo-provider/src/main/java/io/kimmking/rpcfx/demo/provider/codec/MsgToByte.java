package io.kimmking.rpcfx.demo.provider.codec;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/4/13 15:27
 */
@ChannelHandler.Sharable
public class MsgToByte extends MessageToByteEncoder<RpcfxRequest> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcfxRequest rpcfxRequest, ByteBuf byteBuf) throws Exception {
        byteBuf.writeCharSequence(rpcfxRequest.toString(), StandardCharsets.UTF_8);
    }
}
