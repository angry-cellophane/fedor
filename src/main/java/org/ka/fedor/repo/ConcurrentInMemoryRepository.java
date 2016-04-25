package org.ka.fedor.repo;

import org.ka.fedor.model.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;

public class ConcurrentInMemoryRepository implements Repository {

    @Override
    public <T extends Serializable> Node<T> put(T object) {
        throw new NotImplementedException();
    }

    @Override
    public <T extends Serializable> boolean remove(Node<T> node) {
        throw new NotImplementedException();
    }
}
