package org.ka.fedor.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Node<V> {
    UUID getId();
    Optional<V> getValue();
    void addReference(Node<?> object);
    boolean removeReference(Node<?> object);
    List<Node<?>> getImmutableReferences();
    boolean compareAndSetReference(Node<?> expect, Node<?> update);
}
