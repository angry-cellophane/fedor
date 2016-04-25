package org.ka.fedor.api.builder;

import org.ka.fedor.repo.Repository;
import org.ka.fedor.repo.SimpleInMemoryRepository;

public final class InMemoryRepositoryBuilder {

    public InMemoryRepositoryBuilder() {
    }

    public Repository build() {
        return new SimpleInMemoryRepository();
    }
}
