package org.ka.fedor.repo;


import org.ka.fedor.model.IdentityNode;

public interface InMemoryRepository  {
    <T> IdentityNode<T> put(T object);
    <T> boolean remove(IdentityNode<T> node);
}
