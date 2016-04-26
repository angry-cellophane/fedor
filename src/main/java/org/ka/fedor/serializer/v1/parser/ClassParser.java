package org.ka.fedor.serializer.v1.parser;


import org.ka.fedor.serializer.v1.description.ClassDescription;

import java.util.function.Function;

public interface ClassParser extends Function<Class<?>, ClassDescription> {
}
