package org.ka.fedor.repo.file;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class FileDataSupplier<T> implements Supplier<Optional<T>> {

    private final Path dataFilePath;
    private final Function<ByteBuffer, Object> fromBytes;
    private final Supplier<Boolean> isAlive;

    public FileDataSupplier(Path dataFilePath,
                            Function<ByteBuffer, Object> fromBytes,
                            Supplier<Boolean>  isAlive) {
        this.dataFilePath = dataFilePath;
        this.fromBytes = fromBytes;
        this.isAlive = isAlive;
    }

    @Override
    public Optional<T> get() {
        if (!isAlive.get()) return Optional.empty();

        try (RandomAccessFile file = new RandomAccessFile(dataFilePath.toFile(), "rw")) {
            try (FileChannel channel = file.getChannel()) {
                ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
                channel.read(buffer);
                buffer.flip();

                @SuppressWarnings("unchecked") T object = (T) fromBytes.apply(buffer);
                return Optional.of(object);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
