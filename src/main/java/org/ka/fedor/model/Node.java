package org.ka.fedor.model;

import java.util.List;

public interface Node<V> {
    V getValue();
    void addReference(Node<?> object);
    boolean removeReference(Node<?> object);
    List<Node<?>> getImmutableReferences();
    boolean compareAndSetReference(Node<?> expect, Node<?> update);
}
