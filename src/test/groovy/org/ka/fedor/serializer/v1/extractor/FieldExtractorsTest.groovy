package org.ka.fedor.serializer.v1.extractor

import org.ka.fedor.serializer.memory.MemoryManager
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Field
import java.nio.ByteBuffer

class FieldExtractorsTest extends Specification {

    static class Pojo {
        short shortValue;
        int intValue;
        long longValue;
        float floatValue;
        double doubleValue;
        char charValue;
        boolean boolValue;
    }

    static Field getField(String fieldName) {
        Pojo.class.getDeclaredField(fieldName)
    }

    @Unroll
    def 'create field extractor of the #expectedClass type'() {
        when:
        def extractor = FieldExtractors.create(getField(fieldName), [objectFieldOffset: { x -> 1l }] as MemoryManager)
        then:
        extractor.class == expectedClass
        where:
        fieldName     | expectedClass
        'shortValue'  | FieldExtractors.ShortExtractor.class
        'intValue'    | FieldExtractors.IntExtractor.class
        'longValue'   | FieldExtractors.LongExtractor.class
        'floatValue'  | FieldExtractors.FloatExtractor.class
        'doubleValue' | FieldExtractors.DoubleExtractor.class
        'charValue'   | FieldExtractors.CharExtractor.class
        'boolValue'   | FieldExtractors.BooleanExtractor.class
    }

    @Unroll
    def 'put and get value of the #type type'() {
        given:
        ByteBuffer buffer = ByteBuffer.allocate(8)
        when:
        extractor.accept(buffer, value)
        and:
        buffer.flip()
        then:
        value == valueOf(buffer)
        where:
        type      | value       | extractor                                                                                            | valueOf
        'int'     | 1           | new FieldExtractors.IntExtractor([getInt: { object, offset -> 1 }] as MemoryManager, 1)              | { b -> b.getInt() }
        'long'    | 2l          | new FieldExtractors.LongExtractor([getLong: { object, offset -> 2l }] as MemoryManager, 2l)          | { b -> b.getLong() }
        'float'   | 3f          | new FieldExtractors.FloatExtractor([getFloat: { object, offset -> 3f }] as MemoryManager, 3l)        | { b -> b.getFloat() }
        'double'  | 4d          | new FieldExtractors.DoubleExtractor([getDouble: { object, offset -> 4d }] as MemoryManager, 4l)      | { b -> b.getDouble() }
        'char'    | 'c' as char | new FieldExtractors.CharExtractor([getChar: { object, offset -> 'c' as char }] as MemoryManager, 5l) | { b -> b.getChar() }
        'boolean' | true        | new FieldExtractors.BooleanExtractor([getBoolean: { object, offset -> true }] as MemoryManager, 6l)  | { b -> b.getInt() == 1 }
    }
}
