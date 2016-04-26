package org.ka.fedor.serializer.v1;

import org.ka.fedor.serializer.v1.description.ClassDescription;
import org.ka.fedor.serializer.v1.parser.ClassParser;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class SerializerFactory implements Function<Object, Supplier<ByteBuffer>> {

    private final Map<Class<?>, ClassDescription> descriptions;
    private final ClassParser classParser;


    public SerializerFactory(Map<Class<?>, ClassDescription> descriptions, ClassParser classParser) {
        this.descriptions = descriptions;
        this.classParser = classParser;
    }

    @Override
    public Serializer apply(Object o) {
        ClassDescription desc = descriptions.get(o.getClass());
        if (desc == null) {
            desc = classParser.apply(o.getClass());
        }

        return new Serializer(desc, o);
    }
}
