package org.ka.fedor.repo;


import org.ka.fedor.model.Node;

import java.io.Serializable;

public interface Repository {
    <T extends Serializable> Node<T> put(T object);
    <T extends Serializable> boolean remove(Node<T> node);
}
