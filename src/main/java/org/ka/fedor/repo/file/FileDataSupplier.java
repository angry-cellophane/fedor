package org.ka.fedor.repo.file;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Supplier;

public class FileDataSupplier<T> implements Supplier<T> {

    private final Path dataFilePath;
    private final Function<ByteBuffer, Object> fromBytes;

    public FileDataSupplier(Path dataFilePath, Function<ByteBuffer, Object> fromBytes) {
        this.dataFilePath = dataFilePath;
        this.fromBytes = fromBytes;
    }

    @Override
    public T get() {
        try (RandomAccessFile file = new RandomAccessFile(dataFilePath.toFile(), "rw")) {
            try (FileChannel channel = file.getChannel()) {
                ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
                channel.read(buffer);
                buffer.flip();

                @SuppressWarnings("unchecked") T object = (T) fromBytes.apply(buffer);
                return object;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
