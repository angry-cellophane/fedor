package org.ka.fedor.model;

import java.util.UUID;

public interface IdentityNode<V> extends Node<V> {
    UUID getId();
}
