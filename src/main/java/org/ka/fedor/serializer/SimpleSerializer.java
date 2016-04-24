package org.ka.fedor.serializer;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.function.Function;

public class SimpleSerializer implements Function<Object, ByteBuffer> {

    @Override
    public ByteBuffer apply(Object object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream os = new ObjectOutputStream(bos)) {
                os.writeObject(object);
                os.flush();
            }
            return ByteBuffer.wrap(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
