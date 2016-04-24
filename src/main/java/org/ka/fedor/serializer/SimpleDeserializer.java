package org.ka.fedor.serializer;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.function.Function;

public class SimpleDeserializer implements Function<ByteBuffer, Object>{

    @Override
    public Object apply(ByteBuffer byteBuffer) {
        try (ByteArrayInputStream bs = new ByteArrayInputStream(byteBuffer.array())) {
            try (ObjectInputStream os = new ObjectInputStream(bs)) {
                return os.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
