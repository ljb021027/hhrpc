package com.ljb.hhrpc.registry;

import java.io.IOException;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
interface Server {
    void stop();

    void start() throws IOException;

    void register(Class serviceInterface, Class impl);

    boolean isRunning();

    int getPort();
}
