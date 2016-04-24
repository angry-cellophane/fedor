package org.ka.fedor.serializer;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.function.Function;

public class SimpleSerializer implements Function<Object, ByteBuffer> {

    @Override
    public ByteBuffer apply(Object object) {
        try (ByteOutputStream bos = new ByteOutputStream()) {
            try (ObjectOutputStream os = new ObjectOutputStream(bos)) {
                os.writeObject(object);
                os.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return ByteBuffer.wrap(bos.getBytes());
        }
    }
}
