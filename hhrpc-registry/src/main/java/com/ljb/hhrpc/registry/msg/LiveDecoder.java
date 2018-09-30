package com.ljb.hhrpc.registry.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author liujiabei
 * @since 2018/9/30
 */
public class LiveDecoder extends ReplayingDecoder<LiveDecoder.LiveState> { //1

    public enum LiveState { //2
        LENGTH,
        CONTENT
    }


    public LiveDecoder() {
        super(LiveState.LENGTH); // 3
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) { // 4
            case LENGTH:
                int length = byteBuf.readInt();
                if (length > 0) {
                    checkpoint(LiveState.CONTENT); // 5
                } else {
//                    list.add(message); // 6
                }
                break;
            case CONTENT:
//                byte[] bytes = new byte[message.getLength()];
//                byteBuf.readBytes(bytes);
//                String content = new String(bytes);
//                message.setContent(content);
//                list.add(content);
                break;
            default:
                throw new IllegalStateException("invalid state:" + state());
        }
    }
}
