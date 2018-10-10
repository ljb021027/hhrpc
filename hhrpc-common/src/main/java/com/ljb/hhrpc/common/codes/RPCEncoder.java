package com.ljb.hhrpc.common.codes;

import com.ljb.hhrpc.common.serialization.JavaSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author liujiabei
 * @since 2018/10/9
 */
public class RPCEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RPCEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (genericClass.isInstance(msg)) {
            byte[] data = JavaSerialization.objToBytes(msg);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
