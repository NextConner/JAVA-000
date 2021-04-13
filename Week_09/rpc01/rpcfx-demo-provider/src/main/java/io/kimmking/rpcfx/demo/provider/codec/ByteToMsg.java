package io.kimmking.rpcfx.demo.provider.codec;

import com.alibaba.fastjson.JSONObject;
import io.kimmking.rpcfx.util.ByteBufMsgRead;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/4/13 15:25
 */
public class ByteToMsg extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        String msg = ByteBufMsgRead.getByteBufMsg(byteBuf,"");
        JSONObject json = JSONObject.parseObject(msg);
        list.add(json);
    }
}
