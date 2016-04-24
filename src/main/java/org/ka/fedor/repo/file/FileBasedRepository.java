package org.ka.fedor.repo.file;

import org.ka.fedor.model.FileNode;
import org.ka.fedor.model.Node;
import org.ka.fedor.repo.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.function.Function;

public class FileBasedRepository implements Repository {

    private final String dataDirPath;
    private final Function<Object, ByteBuffer> toBytes;
    private final Function<ByteBuffer, Object> fromBytes;

    private final Map<UUID, Node<?>> nodes;

    public FileBasedRepository(String dataDirPath,
                               Function<Object, ByteBuffer> toBytes,
                               Function<ByteBuffer, Object> fromBytes) {
        this.dataDirPath = dataDirPath;
        this.toBytes = toBytes;
        this.fromBytes = fromBytes;
        this.nodes = new WeakHashMap<>();
    }

    @Override
    public <T extends Serializable> Node<T> put(T object) {
        UUID id = UUID.randomUUID();

        ByteBuffer data = toBytes.apply(object);

        Path dataFilePath = dataFilePathBy(id);

        try (RandomAccessFile file = openFile(dataFilePath.toFile())) {
            write(file, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileNode<T> node = new FileNode<>(id, new FileDataSupplier<>(dataFilePath, fromBytes));
        nodes.put(id, node);
        return node;
    }

    private Path dataFilePathBy(UUID id) {
        return Paths.get(dataDirPath, id.toString() + ".dat");
    }

    private RandomAccessFile openFile(File dataFile) {
        try {
            return new RandomAccessFile(dataFile, "rw");
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
    public <T extends Serializable> boolean remove(Node<T> node) {
        return nodes.remove(node.getId()) != null;
    }
}
