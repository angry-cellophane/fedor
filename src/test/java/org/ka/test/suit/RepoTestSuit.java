package org.ka.test.suit;

public interface RepoTestSuit {

    void savePojo() throws Exception;

    void saveListOfPojo() throws Exception;

    void addAndGetReference() throws Exception;

    void addAndRemoveReference() throws Exception;

    void addAndRemoveDetachedReference() throws Exception;

    void addReferenceThenCompareAndSet() throws Exception;

    void removeExistingNode() throws Exception;


    void removeNonExistingNode() throws Exception;

}
