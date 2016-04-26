package org.ka.fedor.serializer.v1.extractor;

import java.nio.ByteBuffer;
import java.util.function.BiConsumer;

public interface FieldExtractor extends BiConsumer<ByteBuffer, Object> {
}
