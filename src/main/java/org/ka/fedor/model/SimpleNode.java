package org.ka.fedor.model;


import java.util.*;
import java.util.function.Supplier;

public class SimpleNode<V> implements Node<V> {

    private final UUID id;
    private final V value;
    private final ArrayList<Node<?>> references;
    private final List<Node<?>> unmodReferences;
    private final Supplier<Boolean> isAlive;

    public SimpleNode(UUID id, V value, Supplier<Boolean> isAlive) {
        this.id = id;
        this.value = value;
        this.isAlive = isAlive;
        references = new ArrayList<>();
        unmodReferences = Collections.unmodifiableList(references);
    }

    @Override
    public Optional<V> getValue() {
        return isAlive.get() ? Optional.of(value) : Optional.empty();
    }

    @Override
    public void addReference(Node<?> object) {
        if (object == null) throw new IllegalArgumentException();

        references.add(object);
    }

    @Override
    public boolean removeReference(Node<?> toBeRemoved) {
        for (ListIterator<Node<?>> it = references.listIterator(); it.hasNext();) {
            Node<?> node = it.next();
            if (node.equals(toBeRemoved)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Node<?>> getImmutableReferences() {
        return unmodReferences;
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

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleNode<?> that = (SimpleNode<?>) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
