package org.ka.fedor.repo.file;

import org.ka.fedor.model.Node;
import org.ka.fedor.repo.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.file.Path;

public class FileBasedRepository implements Repository {

    private final Path dataDir;

    public FileBasedRepository(Path dataDirPath) {
        this.dataDir = dataDirPath;
    }

    @Override
    public <T> Node<T> put(T object) {
        throw new NotImplementedException();
    }

    @Override
    public <T> boolean remove(Node<T> node) {
        throw new NotImplementedException();
    }
}
