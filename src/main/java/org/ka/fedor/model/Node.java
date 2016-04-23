package org.ka.fedor.model;

import java.util.List;
import java.util.UUID;

public interface Node<V> {
    UUID getId();
    V getValue();
    void addReference(Node<?> object);
    boolean removeReference(Node<?> object);
    List<Node<?>> getImmutableReferences();
    boolean compareAndSetReference(Node<?> expect, Node<?> update);
}
