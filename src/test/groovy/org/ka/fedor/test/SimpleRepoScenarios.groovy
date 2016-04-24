package org.ka.fedor.test

import org.ka.fedor.test.suit.Person
import spock.lang.Specification
import spock.lang.Unroll

class SimpleRepoScenarios extends Specification implements RepositoryTestUtils {

    @Unroll
    def 'put a person and get its value from #repo'() {
        given:
        def node = repo.put(object)
        when:
        def value = node.value
        then:
        value.isPresent() == true
        and:
        value.get() == object
        where:
        repo << repos.values()
        object << [new Person(1, 'Alex', 'Rot')] * repos.size()
    }

    @Unroll
    def 'put several persons and get their values from #repo'() {
        given:
        def nodes = persons.collect { repo.put(it) }
        when:
        def values = nodes*.getValue()
        then:
        values*.isPresent() == ([true] * values.size())
        and:
        values*.get() == persons
        where:
        repo << repos.values()
        persons << [ [new Person(24, 'Alex', 'Rot'), new Person(25, 'Bob', 'Kelso'), new Person(26, 'John', 'Dorian')] ] * repos.size()
    }

    @Unroll
    def 'put and remove nodes one by one from #repo'() {
        given:
        def firstRemoveActualResults = []
        when:
        def nodes = persons.collect { p ->
            def node = repo.put(p)
            firstRemoveActualResults << repo.remove(node)
            node
        }
        then:
        firstRemoveActualResults == firstRemoveExpectedResults
        nodes.collect { repo.remove(it) } == secondRemoveExpectedResults
        where:
        repo << repos.values()
        persons << [ [new Person(34, 'Alex', 'Rot'), new Person(35, 'Bob', 'Kelso'), new Person(36, 'John', 'Dorian')] ] * repos.size()
        firstRemoveExpectedResults << [ [true, true, true] ] * repos.size()
        secondRemoveExpectedResults << [ [false, false, false] ] * repos.size()
    }

    @Unroll
    def 'put a person and remove it then getting its value fails from #repo'() {
        when:
        def node = repo.put(person)
        and:
        repo.remove(node)
        and:
        def value = node.getValue()
        then:
        value.isPresent() ==  false
        and:
        notThrown RuntimeException
        where:
        repo << repos.values()
        person << [ new Person(36, 'Charles', 'Kane') ] * repos.size()
    }

}
