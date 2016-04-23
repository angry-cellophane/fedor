package org.ka.fedor.repo;


import org.ka.fedor.model.Node;

public interface Repository {
    <T> Node<T> put(T object);
    <T> boolean remove(Node<T> node);
}
