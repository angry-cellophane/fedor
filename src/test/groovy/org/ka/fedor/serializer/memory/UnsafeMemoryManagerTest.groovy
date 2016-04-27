package org.ka.fedor.serializer.memory

import org.ka.fedor.test.model.PrimitivePojo
import spock.lang.Specification
import spock.lang.Unroll
import sun.misc.Unsafe

import java.lang.reflect.Field

class UnsafeMemoryManagerTest extends Specification {

    static final Unsafe unsafe;

    static {
        def field = Unsafe.class.getDeclaredField("theUnsafe")
        field.accessible = true
        unsafe = field.get(null)
    }

    static Field fieldByName(String fieldName) {
        PrimitivePojo.class.getDeclaredField(fieldName)
    }

    MemoryManager manager = new UnsafeMemoryManager();

    def 'ObjectFieldOffset'() {
        given:
        def field = PrimitivePojo.class.getDeclaredField("intValue")
        expect:
        manager.objectFieldOffset(field) == unsafe.objectFieldOffset(field)
    }

    @Unroll
    def 'get the #name field'() {
        given:
        PrimitivePojo pojo = new PrimitivePojo()
        pojo."$name" = expect
        and:
        def offset = manager.objectFieldOffset(fieldByName(name))
        when:
        def result = manager.invokeMethod(method, [pojo, offset])
        then:
        result == expect
        where:
        name | field | method || expect
        'shortValue' | fieldByName('shortValue') | 'getShort' || 1 as short
        'intValue' | fieldByName('intValue') | 'getInt' || 2 as int
        'longValue' | fieldByName('longValue') | 'getLong' || 3 as long
        'floatValue' | fieldByName('floatValue') | 'getFloat' || 4 as float
        'doubleValue' | fieldByName('doubleValue') | 'getDouble' || 5 as double
        'charValue' | fieldByName('charValue') | 'getChar' || 'a' as char
        'boolValue' | fieldByName('boolValue') | 'getBoolean' || true
    }
}
