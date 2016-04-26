package org.ka.fedor.serializer.v1.parser;

import org.ka.fedor.serializer.memory.MemoryManager;
import org.ka.fedor.serializer.v1.description.ClassDescription;
import org.ka.fedor.serializer.v1.extractor.FieldExtractor;
import org.ka.fedor.serializer.v1.extractor.FieldExtractors;

import java.lang.reflect.Field;

public class SimpleClassParser implements ClassParser {

    private final MemoryManager memoryManager;

    public SimpleClassParser(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }

    @Override
    public ClassDescription apply(Class<?> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        FieldExtractor[] extractors = new FieldExtractor[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            extractors[i] = FieldExtractors.create(field, memoryManager);
        }
        return null;
    }
}
