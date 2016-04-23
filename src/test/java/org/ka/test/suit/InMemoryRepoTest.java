package org.ka.test.suit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ka.fedor.api.Repositories;
import org.ka.fedor.model.IdentityNode;
import org.ka.fedor.model.Node;
import org.ka.fedor.repo.InMemoryRepository;
import org.ka.test.suit.Person;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class InMemoryRepoTest implements RepoTestSuit {

    InMemoryRepository repo;

    @Before
    public void setup() {
        repo = Repositories
                .inMemoryRepository()
                .build();
    }

    @Test
    public void savePojo() throws IOException {
        Person person = new Person(1, "Alex", "John");

        Node<Person> personNode = repo.put(person);
        Person value = personNode.getValue();

        Assert.assertEquals(person, value);
    }

    @Test
    public void saveListOfPojo() throws IOException {
        List<Person> persons = dummyPersons();

        Node<List<Person>> personNode = repo.put(persons);
        List<Person> value = personNode.getValue();

        Assert.assertEquals(persons, value);
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

        IdentityNode<Person> personNode = repo.put(person);
        Assert.assertTrue(repo.remove(personNode));
    }

    @Test
    public void removeNonExistingNode() {
        Person person = new Person(23, "Ivan", "Drago");

        IdentityNode<Person> personNode = repo.put(person);
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
