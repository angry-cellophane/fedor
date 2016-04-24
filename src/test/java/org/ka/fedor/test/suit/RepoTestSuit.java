package org.ka.fedor.test.suit;

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
import java.util.List;
import java.util.Optional;

@RunWith(Parameterized.class)
public class RepoTestSuit {

    public static final String DATA_DIR = "./data_RepoTestSuit";

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
    public void savePojoAndGetValueFromNode() throws IOException {
        Person person = new Person(1, "Alex", "John");

        Node<Person> personNode = repo.put(person);
        Optional<Person> actualValue = personNode.getValue();
        Assert.assertTrue(actualValue.isPresent());
        Person value = actualValue.get();

        Assert.assertEquals(person, value);
    }

    @Test
    public void addAndGetReference() {
        Person rootPerson = new Person(4, "John", "Smith");
        Person childPerson = new Person(5, "Chris", "Walker");

        Node<Person> rootNode = repo.put(rootPerson);
        Node<Person> childNode = repo.put(childPerson);
        rootNode.addReference(childNode);

        Assert.assertEquals(1, rootNode.getImmutableReferences().size());
        Assert.assertEquals(childNode, rootNode.getImmutableReferences().get(0));
        Assert.assertEquals(childNode.getValue(), rootNode.getImmutableReferences().get(0).getValue());
    }

    @Test
    public void addAndRemoveReference() {
        Person rootPerson = new Person(6, "John", "Smith");
        Person childPerson = new Person(7, "Chris", "Walker");

        Node<Person> rootNode = repo.put(rootPerson);
        Node<Person> childNode = repo.put(childPerson);
        rootNode.addReference(childNode);

        Assert.assertEquals(1, rootNode.getImmutableReferences().size());
        Assert.assertEquals(childNode, rootNode.getImmutableReferences().get(0));
        Assert.assertEquals(childNode.getValue(), rootNode.getImmutableReferences().get(0).getValue());

        boolean removeResult = rootNode.removeReference(childNode);
        Assert.assertTrue(removeResult);

        Assert.assertTrue(rootNode.getImmutableReferences().isEmpty());
    }

    @Test
    public void addAndRemoveDetachedReference() {
        Person rootPerson = new Person(8, "John", "Smith");
        Person childPerson = new Person(9, "Chris", "Walker");

        Node<Person> rootNode = repo.put(rootPerson);
        Node<Person> childNode = repo.put(childPerson);

        boolean removeResult = rootNode.removeReference(childNode);
        Assert.assertFalse(removeResult);

        Assert.assertTrue(rootNode.getImmutableReferences().isEmpty());
    }

    @Test
    public void addReferenceThenCompareAndSet() {
        Person rootPerson = new Person(10, "John", "Smith");
        Person childPerson = new Person(11, "Chris", "Walker");

        Node<Person> rootNode = repo.put(rootPerson);
        Node<Person> childNode = repo.put(childPerson);

        rootNode.addReference(childNode);

        Person newChildPerson = new Person(12, "John", "Smith");
        Node<Person> newChildPersonNode = repo.put(newChildPerson);

        boolean result = rootNode.compareAndSetReference(childNode, newChildPersonNode);
        Assert.assertTrue(result);

        Assert.assertEquals(1, rootNode.getImmutableReferences().size());
        Assert.assertEquals(newChildPersonNode, rootNode.getImmutableReferences().get(0));
        Assert.assertEquals(newChildPersonNode.getValue(), rootNode.getImmutableReferences().get(0).getValue());
    }

    @Test
    public void removeExistingNode() {
        Person person = new Person(23, "Ivan", "Drago");

        Node<Person> personNode = repo.put(person);
        Assert.assertTrue(repo.remove(personNode));
    }

    @Test
    public void removeNonExistingNode() {
        Person person = new Person(23, "Ivan", "Drago");

        Node<Person> personNode = repo.put(person);
        Assert.assertTrue(repo.remove(personNode));
        Assert.assertFalse(repo.remove(personNode));
    }

    private List<Person> dummyPersons() {
        return Arrays.asList(
                new Person(1, "John", "Travolt"),
                new Person(2, "Alex", "Sober"),
                new Person(3, "Michael", "Black")
        );
    }

}
