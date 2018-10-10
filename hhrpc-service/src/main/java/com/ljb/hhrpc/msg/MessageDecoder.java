package com.ljb.hhrpc.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int size = in.readableBytes();
        if (size < 0 || size > (1 << 20)) {
            out.add("errorrrrr");
            return;
        }
        byte[] bytes = new byte[size];
        in.readBytes(bytes);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
        ObjectInputStream input = new ObjectInputStream(byteIn);
        String serviceName = input.readUTF();
        String methodName = input.readUTF();
        Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
        Object[] arguments = (Object[]) input.readObject();
        Class serviceClass = MessageRegistry.get(serviceName);
        if (serviceClass == null) {
            throw new ClassNotFoundException(serviceName + " not found");
        }
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        Object result = method.invoke(serviceClass.newInstance(), arguments);

        out.add(result);
    }

    private String readStr(ByteBuf in) {
        // 字符串先长度后字节数组，统一UTF8编码
        int len = in.readInt();
        if (len < 0 || len > (1 << 20)) {
            throw new DecoderException("string too long len=" + len);
        }
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        return new String(bytes, Charset.forName("utf-8"));
    }

}
