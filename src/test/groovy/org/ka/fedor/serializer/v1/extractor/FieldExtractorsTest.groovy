package org.ka.fedor.serializer.v1.extractor

import org.ka.fedor.serializer.memory.MemoryManager
import spock.lang.Specification
import spock.lang.Unroll

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

    @Unroll
    def 'create field extractor of the #expectedClass type'() {
        when:
        def extractor = FieldExtractors.create(field, [objectFieldOffset: { x -> 1l }] as MemoryManager)
        then:
        extractor.class == expectedClass
        where:
        field                                     | expectedClass
        Pojo.class.getDeclaredField('shortValue') | FieldExtractors.ShortExtractor.class
        Pojo.class.getDeclaredField('intValue') | FieldExtractors.IntExtractor.class
        Pojo.class.getDeclaredField('longValue') | FieldExtractors.LongExtractor.class
        Pojo.class.getDeclaredField('floatValue') | FieldExtractors.FloatExtractor.class
        Pojo.class.getDeclaredField('doubleValue') | FieldExtractors.DoubleExtractor.class
        Pojo.class.getDeclaredField('charValue') | FieldExtractors.CharExtractor.class
        Pojo.class.getDeclaredField('boolValue') | FieldExtractors.BooleanExtractor.class
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
