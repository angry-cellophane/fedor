package org.ka.fedor.api;


import com.sun.javafx.runtime.SystemProperties;
import org.ka.fedor.repo.Repository;
import org.ka.fedor.repo.SimpleInMemoryRepository;
import org.ka.fedor.repo.file.FileBasedRepository;
import org.ka.fedor.serializer.SimpleDeserializer;
import org.ka.fedor.serializer.SimpleSerializer;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.IntStream;

public class Repositories {

    public static final class InMemoryRepositoryBuilder {

        private InMemoryRepositoryBuilder() {}

        public Repository build() {
            return new SimpleInMemoryRepository();
        }
    }

    public static final class FileBasedRepositoryBuilder {

        public static final String FEDOR_DATA_DIR = "FEDOR_DATA_DIR";
        private static final String DIR_FROM_ENV_NOT_EXIST_ERR_MESSAGE = FEDOR_DATA_DIR + " = %s: dir doesn't exist";
        private Path dataDirPath;

        private FileBasedRepositoryBuilder() {}

        public FileBasedRepositoryBuilder dataDirPath(String dataDirPath) {
            Path path = Paths.get(dataDirPath);

            if (!Files.exists(path)) {
                throw new IllegalArgumentException("Path "+dataDirPath +" doesn't exist");
            }

            this.dataDirPath = path;
            return this;
        }

        public Repository build() {
            if (dataDirPath == null) {
                dataDirPath = pathFromEnvVar();
                if (dataDirPath != null) {
                    if (!Files.exists(dataDirPath)) {
                        throw new IllegalArgumentException(String.format(DIR_FROM_ENV_NOT_EXIST_ERR_MESSAGE, dataDirPath.toString()));
                    }
                } else {
                    dataDirPath = defaultPath();
                    if (!Files.exists(dataDirPath)) {
                        try {
                            Files.createDirectory(dataDirPath);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            return new FileBasedRepository(dataDirPath.toAbsolutePath().toString(),
                    new SimpleSerializer(),
                    new SimpleDeserializer()
            );
        }

        private Path pathFromEnvVar() {
            String dirName = SystemProperties.getProperty(FEDOR_DATA_DIR);
            return dirName == null || "".equals(dirName) ? null : Paths.get(dirName);
        }

        private Path defaultPath() {
            return Paths.get("./data");
        }
    }

    public static InMemoryRepositoryBuilder inMemoryRepository() {
        return new InMemoryRepositoryBuilder();
    }

    public static FileBasedRepositoryBuilder fileRepository() {
        return new FileBasedRepositoryBuilder();
    }

    private Repositories() {}
}
