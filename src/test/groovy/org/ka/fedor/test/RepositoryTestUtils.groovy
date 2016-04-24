package org.ka.fedor.test

import org.ka.fedor.api.Repositories

trait RepositoryTestUtils {

    def repos = { ->
        new File("./data_${this.class.simpleName}").mkdir()

        [
                inMemory: Repositories.inMemoryRepository().build(),
                file    : Repositories.fileRepository().dataDirPath("./data_${this.class.simpleName}").build()
        ]
    }.call()

    def cleanupSpec() {
        def dataDir = new File("./data_${this.class.simpleName}")
        dataDir.listFiles().each { it.delete() }
        dataDir.delete()
    }

}
