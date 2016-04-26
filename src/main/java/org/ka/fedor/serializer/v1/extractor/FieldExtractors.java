package org.ka.fedor.serializer.v1.extractor;


import org.ka.fedor.serializer.memory.MemoryManager;

import java.nio.ByteBuffer;

public class FieldExtractors {

    static abstract class AbstractExtractor implements FieldExtractor {
        protected final MemoryManager memoryManager;
        protected final long offset;

        protected AbstractExtractor(MemoryManager memoryManager, long offset) {
            this.memoryManager = memoryManager;
            this.offset = offset;
        }
    }

    public static class ShortExtractor extends AbstractExtractor {

        public ShortExtractor(MemoryManager memoryManager, long offset) {
            super(memoryManager, offset);
        }

        @Override
        public void accept(ByteBuffer byteBuffer, Object object) {
            byteBuffer.putShort(memoryManager.getShort(object, offset));
        }
    }

    public static class IntExtractor extends AbstractExtractor {

        public IntExtractor(MemoryManager memoryManager, long offset) {
            super(memoryManager, offset);
        }

        @Override
        public void accept(ByteBuffer byteBuffer, Object object) {
            byteBuffer.putInt(memoryManager.getInt(object, offset));
        }
    }

    public static class LongExtractor extends AbstractExtractor {

        public LongExtractor(MemoryManager memoryManager, long offset) {
            super(memoryManager, offset);
        }

        @Override
        public void accept(ByteBuffer byteBuffer, Object object) {
            byteBuffer.putLong(memoryManager.getLong(object, offset));
        }
    }

    public static class FloatExtractor extends AbstractExtractor {

        public FloatExtractor(MemoryManager memoryManager, long offset) {
            super(memoryManager, offset);
        }

        @Override
        public void accept(ByteBuffer byteBuffer, Object object) {
            byteBuffer.putFloat(memoryManager.getFloat(object, offset));
        }
    }

    public static class DoubleExtractor extends AbstractExtractor {

        public DoubleExtractor(MemoryManager memoryManager, long offset) {
            super(memoryManager, offset);
        }

        @Override
        public void accept(ByteBuffer byteBuffer, Object object) {
            byteBuffer.putDouble(memoryManager.getDouble(object, offset));
        }
    }

    public static class CharExtractor extends AbstractExtractor {

        public CharExtractor(MemoryManager memoryManager, long offset) {
            super(memoryManager, offset);
        }

        @Override
        public void accept(ByteBuffer byteBuffer, Object object) {
            byteBuffer.putChar(memoryManager.getChar(object, offset));
        }
    }

    public static class BooleanExtractor extends AbstractExtractor {

        public BooleanExtractor(MemoryManager memoryManager, long offset) {
            super(memoryManager, offset);
        }

        @Override
        public void accept(ByteBuffer byteBuffer, Object object) {
            byteBuffer.putInt(memoryManager.getBoolean(object, offset) ? 1 : 0);
        }
    }


    private FieldExtractors(){}
}
