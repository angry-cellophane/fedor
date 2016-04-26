package org.ka.fedor.serializer.v1.description;

import org.ka.fedor.serializer.v1.extractor.FieldExtractor;

public class ClassDescription {

    public final FieldExtractor[] extractors;
    public final int objectSize;

    public ClassDescription(FieldExtractor[] extractors, int objectSize) {
        this.extractors = extractors;
        this.objectSize = objectSize;
    }


}
