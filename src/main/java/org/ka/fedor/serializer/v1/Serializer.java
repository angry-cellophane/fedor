package org.ka.fedor.serializer.v1;


import org.ka.fedor.serializer.v1.description.ClassDescription;
import org.ka.fedor.serializer.v1.extractor.FieldExtractor;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

class Serializer implements Supplier<ByteBuffer> {


    private final ClassDescription description;
    private final Object object;

    Serializer(ClassDescription description,
               Object object) {
        this.description = description;
        this.object = object;
    }

    @Override
    public ByteBuffer get() {
        ByteBuffer buffer = ByteBuffer.allocate(description.objectSize);
        for (FieldExtractor extractor : description.extractors) {
            extractor.accept(buffer, object);
        }
        return buffer;
    }
}
