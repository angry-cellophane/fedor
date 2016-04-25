package org.ka.fedor.api;


import org.ka.fedor.api.builder.FileBasedRepositoryBuilder;
import org.ka.fedor.api.builder.InMemoryRepositoryBuilder;

public class Repositories {

    public static InMemoryRepositoryBuilder inMemoryRepository() {
        return new InMemoryRepositoryBuilder();
    }

    public static FileBasedRepositoryBuilder fileRepository() {
        return new FileBasedRepositoryBuilder();
    }

    private Repositories() {}
}
