package org.ka.fedor.serializer.v1;

import java.nio.ByteBuffer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ObjectToByteBuffer implements Function<Object, ByteBuffer> {

    private final Function<Object, Supplier<ByteBuffer>> serializerFactory;

    public ObjectToByteBuffer(Function<Object, Supplier<ByteBuffer>> serializerFactory) {
        this.serializerFactory = serializerFactory;
    }

    @Override
    public ByteBuffer apply(Object o) {
        return serializerFactory.apply(o).get();
    }

}
