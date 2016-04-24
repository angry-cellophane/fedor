package org.ka.fedor.repo;

import org.ka.fedor.model.Node;
import org.ka.fedor.model.SimpleNode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleInMemoryRepository implements Repository {

    private final Map<UUID, SimpleNode<?>> objects;

    public SimpleInMemoryRepository() {
        this.objects = new HashMap<>();
    }

    @Override
    public <T extends Serializable> Node<T> put(T object){
        UUID id = UUID.randomUUID();
        SimpleNode<T> node = new SimpleNode<>(id, object);
        objects.put(id, node);
        return  node;
    }

    @Override
    public <T extends Serializable> boolean remove(Node<T> node) {
        return objects.remove(node.getId()) != null;
    }

    @Override
    public String toString() {
        return "non-thread safe in-memory repository";
    }
}
