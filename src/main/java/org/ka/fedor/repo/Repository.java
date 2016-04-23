package org.ka.fedor.repo;

import org.ka.fedor.model.Node;

import java.util.Optional;
import java.util.UUID;

public interface Repository {
    <T, N extends Node<T>> N put(T object);
    <T, N extends Node<T>> boolean remove(N node);
}
