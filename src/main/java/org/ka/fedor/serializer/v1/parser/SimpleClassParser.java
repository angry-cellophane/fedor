package org.ka.fedor.serializer.v1.parser;

import org.ka.fedor.serializer.memory.MemoryManager;
import org.ka.fedor.serializer.v1.description.ClassDescription;

public class SimpleClassParser implements ClassParser {

    private final MemoryManager memoryManager;

    public SimpleClassParser(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    @Override
    public ClassDescription apply(Class<?> aClass) {
        return null;
    }
}
