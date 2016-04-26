package org.ka.fedor.serializer.memory;


public interface MemoryManager {
    int getInt(Object object, long offset);
    long getLong(Object object, long offset);
    short getShort(Object object, long offset);
    float getFloat(Object object, long offset);
    double getDouble(Object object, long offset);
    char getChar(Object object, long offset);
    boolean getBoolean(Object object, long offset);
}
