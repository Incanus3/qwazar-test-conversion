package cz.sentica.qwazar.auditing

import cz.sentica.qwazar.auditing.models.EntityWithoutConversion
import cz.sentica.qwazar.auditing.models.query.QEntityWithoutConversion
import cz.sentica.qwazar.auditing.test.TestAuditingPersistenceConfiguration
import io.ebean.Database
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        Dummy::class,
        TestAuditingPersistenceConfiguration::class,
    ],
)
internal class EntityWithoutConversionTest {
    @Autowired
    lateinit var database: Database

    private lateinit var nullTrace: EntityWithoutConversion
    private lateinit var registryTrace: EntityWithoutConversion

    @BeforeEach
    fun setUp() {
        nullTrace = EntityWithoutConversion()
        registryTrace = EntityWithoutConversion(
            resourceAddress = "Artifact:test/Test",
        )

        database.saveAll(nullTrace, registryTrace)
    }

    @AfterEach
    fun tearDown() {
        QEntityWithoutConversion(database).delete()
    }

    @Test
    fun testSavingAndFetching() {
        val reloaded = QEntityWithoutConversion(database).setId(registryTrace.gid).findOne()!!

        assertEquals(registryTrace.resourceAddress, reloaded.resourceAddress)
    }

    @Test
    fun queryingByAddress() {
        val nullTraces = QEntityWithoutConversion(database)
            .where().resourceAddress.equalTo("*")
            .findList()

        nullTraces shouldBe listOf(nullTrace)
    }
}
