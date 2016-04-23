package org.ka.fedor.repo.file;

import org.ka.fedor.model.Node;
import org.ka.fedor.repo.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Function;

public class FileBasedRepository implements Repository {

    private final String dataDirPath;
    private final Function<Object, ByteBuffer> toBytes;
    private final Function<ByteBuffer, Object> fromBytes;

    public FileBasedRepository(String dataDirPath,
                               Function<Object, ByteBuffer> toBytes,
                               Function<ByteBuffer, Object> fromBytes) {
        this.dataDirPath = dataDirPath;
        this.toBytes = toBytes;
        this.fromBytes = fromBytes;
    }

    @Override
    public <T> Node<T> put(T object) {
        UUID id = UUID.randomUUID();

        ByteBuffer data = toBytes.apply(object);

        try (RandomAccessFile file = findFile(id, data.capacity())) {
            write(file, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        throw new NotImplementedException();
    }

    private RandomAccessFile findFile(UUID id, int length) {
        try {
            return new RandomAccessFile(new File(dataDirPath, id.toString() + ".dat"), "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(RandomAccessFile file, ByteBuffer data) throws IOException {
        try (FileChannel channel = file.getChannel()) {
            channel.write(data);
        }
    }

    @Override
    public <T> boolean remove(Node<T> node) {
        throw new NotImplementedException();
    }
}
