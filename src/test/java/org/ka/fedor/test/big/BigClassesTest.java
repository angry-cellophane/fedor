package org.ka.fedor.test.big;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.ka.fedor.api.Repositories;
import org.ka.fedor.model.Node;
import org.ka.fedor.repo.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@RunWith(Parameterized.class)
public class BigClassesTest {

    private static final String DATA_DIR = "./data_BigClassesTest";

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> repos() {
        new File(DATA_DIR).mkdir();

        return Arrays.asList( new Object[][] {
                { "in memory", Repositories.inMemoryRepository().build() },
                { "file", Repositories.fileRepository().dataDirPath(DATA_DIR).build() }
        });
    }

    @Parameterized.Parameter(value = 0)
    public String testName;
    @Parameterized.Parameter(value = 1)
    public Repository repo;

    @AfterClass
    public static void cleanup() throws IOException {
        for (File file : Paths.get(DATA_DIR).toFile().listFiles()) {
            System.out.println("delete " + file.getAbsoluteFile() + " = "+file.delete());
        }
        Files.delete(Paths.get(DATA_DIR));
    }


    @Test
    public void putClassWithBigIntArrays() {
        int[] array1 = createDummyIntArray(4_000_000);
        int[] array2 = createDummyIntArray(7_000_000);

        BigIntArray bigObject = new BigIntArray(array1, array2);

        Node<BigIntArray> node = repo.put(bigObject);
        Assert.assertNotNull(node);
        Assert.assertNotNull(node.getId());
    }

    @Test
    public void putClassWithBigIntArraysAndGetNodeValue() {
        int[] array1 = createDummyIntArray(4_000_000);
        int[] array2 = createDummyIntArray(7_000_000);

        BigIntArray bigObject = new BigIntArray(array1, array2);

        Node<BigIntArray> node = repo.put(bigObject);
        Optional<BigIntArray> actualValue = node.getValue();
        Assert.assertTrue(actualValue.isPresent());
        BigIntArray value = actualValue.get();
        Assert.assertEquals(bigObject, value);
    }

    private int[] createDummyIntArray(int size) {
        Random random = new Random();

        int[] ints = new int[size];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt();
        }
        return ints;
    }
}
