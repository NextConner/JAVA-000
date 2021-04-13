package io.kimmking.rpcfx.util;

import io.netty.buffer.ByteBuf;
import org.springframework.util.StringUtils;

/**
 * @Author: zoujintao@daoran.tv
 * @Date: 2021/4/12 16:44
 */
public class ByteBufMsgRead {
    public static String getByteBufMsg(ByteBuf byteBuf, String charset) {

        if (!StringUtils.hasLength(charset)) {
            charset = "UTF-8";
        }
        StringBuffer message = new StringBuffer();
        if(byteBuf.isReadable()){

            //支撑数组模式
            if (byteBuf.hasArray()) {
                byte[] array = byteBuf.array();
                //读起始
                int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
                int length = byteBuf.readableBytes();
                for (int i = 0; i < array.length; i++) {
                    message.append((char) array[i]);
                }
            } else {
                //直接缓冲区模式
                int length = byteBuf.readableBytes();
                byte[] data = new byte[length];
                byteBuf.getBytes(byteBuf.readerIndex(), data);
                return getMessage(data);
            }
        }
        return message.toString();
    }

    private static String getMessage(byte[] data) {
        StringBuffer message = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            message.append((char) data[i]);
        }
        return message.toString();
    }

}
