package cz.sentica.qwazar.auditing

import cz.sentica.qwazar.auditing.address.Address
import cz.sentica.qwazar.auditing.address.NULL_ADDRESS
import cz.sentica.qwazar.auditing.address.ResourceType
import cz.sentica.qwazar.auditing.models.DatabaseAuditTrace
import cz.sentica.qwazar.auditing.models.query.QDatabaseAuditTrace
import cz.sentica.qwazar.auditing.test.TestAuditingPersistenceConfiguration
import io.ebean.Database
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

class Dummy

@SpringBootTest(
    classes = [
        Dummy::class,
        TestAuditingPersistenceConfiguration::class,
    ],
)
internal class DatabaseAuditTraceAddressConversionTest {
    @Autowired
    lateinit var database: Database

    private lateinit var nullTrace: DatabaseAuditTrace
    private lateinit var registryTrace: DatabaseAuditTrace

    @BeforeEach
    fun setUp() {
        nullTrace = DatabaseAuditTrace()
        registryTrace = DatabaseAuditTrace(
            resourceAddress = Address(ResourceType.Artifact, "test", "Test"),
        )

        database.saveAll(nullTrace, registryTrace)
    }

    @AfterEach
    fun tearDown() {
        QDatabaseAuditTrace(database).delete()
    }

    @Test
    fun testSavingAndFetching() {
        val reloaded = QDatabaseAuditTrace(database).setId(registryTrace.gid).findOne()!!

        println(registryTrace.resourceAddress)
        println(reloaded.resourceAddress)
        assertEquals(registryTrace.resourceAddress, reloaded.resourceAddress)
    }

    @Test
    fun queryingByAddress() {
        val nullTraces = QDatabaseAuditTrace(database)
            .where().resourceAddress.equalTo(NULL_ADDRESS)
            .findList()

        nullTraces shouldBe listOf(nullTrace)
    }
}
