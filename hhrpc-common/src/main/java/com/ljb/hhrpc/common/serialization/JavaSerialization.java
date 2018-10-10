package com.ljb.hhrpc.common.serialization;

import java.io.*;

/**
 * @author liujiabei
 * @since 2018/10/9
 */
public class JavaSerialization {

    public static <T> T bytesToObj(byte[] bytes,Class<T> clazz) {

        try (
                ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
                ObjectInputStream input = new ObjectInputStream(byteIn);
        ) {

            return clazz.cast(input.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] objToBytes(Object obj) {

        try (
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
        ) {
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            System.out.println(os.toByteArray().length);
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
