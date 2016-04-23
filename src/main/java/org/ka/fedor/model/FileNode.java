package org.ka.fedor.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FileNode<T> implements Node<T> {

    private final UUID id;
    private final ArrayList<Node<?>> references;
    private final List<Node<?>> imReferences;

    public FileNode(UUID id) {
        this.id = id;

        this.references = new ArrayList<>();
        this.imReferences = Collections.unmodifiableList(references);
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public T getValue() {
        throw new NotImplementedException();
    }

    @Override
    public void addReference(Node<?> object) {
        this.references.add(object);
    }

    @Override
    public boolean removeReference(Node<?> object) {
        return this.references.remove(object);
    }

    @Override
    public List<Node<?>> getImmutableReferences() {
        return imReferences;
    }

    @Override
    public boolean compareAndSetReference(Node<?> expect, Node<?> update) {
        throw new NotImplementedException();
    }
}
