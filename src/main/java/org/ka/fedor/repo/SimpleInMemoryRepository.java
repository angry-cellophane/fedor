package org.ka.fedor.repo;

import org.ka.fedor.model.IdentityNode;
import org.ka.fedor.model.Node;
import org.ka.fedor.model.SimpleNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SimpleInMemoryRepository implements InMemoryRepository {

    private final Map<UUID, SimpleNode<?>> objects;

    public SimpleInMemoryRepository() {
        this.objects = new HashMap<>();
    }

    @Override
    public <T> IdentityNode<T> put(T object){
        UUID id = UUID.randomUUID();
        SimpleNode<T> node = new SimpleNode<>(id, object);
        objects.put(id, node);
        return  node;
    }

    @Override
    public <T> boolean remove(IdentityNode<T> node) {
        return objects.remove(node.getId()) != null;
    }
}
