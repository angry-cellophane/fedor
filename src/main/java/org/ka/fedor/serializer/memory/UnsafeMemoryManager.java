package org.ka.fedor.serializer.memory;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeMemoryManager implements MemoryManager {

    private static final Unsafe U;

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            U = (Unsafe) unsafeField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public UnsafeMemoryManager() {}

    @Override
    public int getInt(Object object, long offset) {
        return U.getInt(object, offset);
    }

    @Override
    public long getLong(Object object, long offset) {
        return U.getLong(object, offset);
    }

    @Override
    public short getShort(Object object, long offset) {
        return U.getShort(object, offset);
    }

    @Override
    public float getFloat(Object object, long offset) {
        return U.getFloat(object, offset);
    }

    @Override
    public double getDouble(Object object, long offset) {
        return U.getDouble(object, offset);
    }

    @Override
    public char getChar(Object object, long offset) {
        return U.getChar(object, offset);
    }

    @Override
    public boolean getBoolean(Object object, long offset) {
        return U.getBoolean(object, offset);
    }

    @Override
    public long objectFieldOffset(Field field) {
        return U.objectFieldOffset(field);
    }
}
