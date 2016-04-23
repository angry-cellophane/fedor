package org.ka.fedor.api;


import org.ka.fedor.repo.Repository;
import org.ka.fedor.repo.SimpleInMemoryRepository;

public class Repositories {

    public static final class RepositoryBuilder {

        public RepositoryBuilder() {
        }

        public InMemoryRepositoryBuilder inMemoryRepository() {
            return new InMemoryRepositoryBuilder();
        }
    }

    public static final class InMemoryRepositoryBuilder {

        InMemoryRepositoryBuilder() {
        }

        public Repository build() {
            return new SimpleInMemoryRepository();
        }
    }

    public static InMemoryRepositoryBuilder inMemoryRepository() {
        return new InMemoryRepositoryBuilder();
    }


    private Repositories() {}
}
