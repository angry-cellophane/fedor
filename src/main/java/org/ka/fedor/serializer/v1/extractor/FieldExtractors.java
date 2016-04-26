package org.ka.fedor.serializer.v1.extractor;


import org.ka.fedor.serializer.memory.MemoryManager;

import java.lang.reflect.Field;
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


    public static FieldExtractor create(Field field, MemoryManager memoryManager) {
        long offset = memoryManager.objectFieldOffset(field);

        Class<?> type = field.getType();
        if (!type.isPrimitive()) {
            throw new RuntimeException("Not supported yet");
        }

        if (type == Short.TYPE) {
            return new ShortExtractor(memoryManager, offset);
        } else if (type == Integer.TYPE) {
            return new IntExtractor(memoryManager, offset);
        } else if (type == Long.TYPE) {
            return new LongExtractor(memoryManager, offset);
        } else if (type == Float.TYPE) {
            return new FloatExtractor(memoryManager, offset);
        } else if (type == Double.TYPE) {
            return new DoubleExtractor(memoryManager, offset);
        } else if (type == Character.TYPE) {
            return new CharExtractor(memoryManager, offset);
        } else if (type == Boolean.TYPE) {
            return new BooleanExtractor(memoryManager, offset);
        }

        throw new IllegalArgumentException("Unknown type: "+type);
    }

    private FieldExtractors(){}
}
