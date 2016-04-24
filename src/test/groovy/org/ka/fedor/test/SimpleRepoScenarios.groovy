package org.ka.fedor.test

import org.ka.fedor.test.suit.Person
import spock.lang.Specification
import spock.lang.Unroll

class SimpleRepoScenarios extends Specification implements RepositoryTestUtils {

    @Unroll
    def 'simple test for #repo'() {
        given:
        def node = repo.put(object)
        expect:
        node.getValue() == object
        where:
        repo              | object
        repos['file']     | new Person(1, 'Alex', 'Rot')
        repos['inMemory'] | new Person(1, 'Alex', 'Rot')
    }

}
