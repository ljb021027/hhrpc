package com.ljb.hhrpc.common.codes;

import com.ljb.hhrpc.common.serialization.JavaSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author liujiabei
 * @since 2018/10/9
 */
public class RPCDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public RPCDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(JavaSerialization.bytesToObj(data, genericClass));

    }

}
