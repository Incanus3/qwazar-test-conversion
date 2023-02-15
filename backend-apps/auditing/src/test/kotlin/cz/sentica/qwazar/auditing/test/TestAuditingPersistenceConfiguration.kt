package cz.sentica.qwazar.auditing.test

import com.fasterxml.jackson.databind.ObjectMapper
import cz.sentica.qwazar.auditing.models.DatabaseAuditTrace
import cz.sentica.qwazar.auditing.models.EntityWithoutConversion
import io.ebean.Database
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.datasource.DataSourceConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@TestConfiguration
@Import(JacksonAutoConfiguration::class)
class TestAuditingPersistenceConfiguration {
    @Autowired
    lateinit var objMapper: ObjectMapper

    @Bean
    fun testRestDataSourceConfig() = DataSourceConfig().apply {
        username = "sa"
        password = "sa"
        url = "jdbc:h2:mem:restdb"
        driver = "org.h2.Driver"
    }

    @Bean
    fun testRestDatabaseConfig() = DatabaseConfig().apply {
        name = "rest"

        isDdlRun = true
        isDdlGenerate = true
        isDefaultServer = false

        objectMapper = objMapper
        dataSourceConfig = testRestDataSourceConfig()

        classes = listOf(
            DatabaseAuditTrace::class.java,
            EntityWithoutConversion::class.java,
        )
    }

    @Bean
    fun restDatabase(): Database = DatabaseFactory.create(testRestDatabaseConfig())
}
