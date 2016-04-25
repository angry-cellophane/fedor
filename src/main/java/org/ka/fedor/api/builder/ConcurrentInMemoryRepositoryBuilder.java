package org.ka.fedor.api.builder;


import org.ka.fedor.repo.ConcurrentInMemoryRepository;

public class ConcurrentInMemoryRepositoryBuilder {

    public ConcurrentInMemoryRepository build() {
        return new ConcurrentInMemoryRepository();
    }
}
