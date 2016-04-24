package org.ka.fedor.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class FileNode<T> implements Node<T> {

    private final UUID id;
    private final ArrayList<Node<?>> references;
    private final List<Node<?>> imReferences;
    private final Supplier<T> valueSupplier;

    public FileNode(UUID id, Supplier<T> valueSupplier) {
        this.id = id;
        this.valueSupplier = valueSupplier;

        this.references = new ArrayList<>();
        this.imReferences = Collections.unmodifiableList(references);
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public T getValue() {
        return valueSupplier.get();
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
        for (int i = 0; i < references.size(); i++) {
            Node<?> node = references.get(i);
            if (node == expect) {
                references.set(i, update);
                return true;
            }
        }
        return false;
    }
}
