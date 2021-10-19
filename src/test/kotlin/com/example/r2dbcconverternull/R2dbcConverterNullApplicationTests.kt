package com.example.r2dbcconverternull

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.convert.R2dbcConverter
import org.springframework.r2dbc.core.DatabaseClient
import reactor.kotlin.test.test


val objectMapper = ObjectMapper()

@SpringBootTest
class R2dbcConverterNullApplicationTests {

    @Autowired
    lateinit var databaseClient: DatabaseClient

    @Autowired
    lateinit var converter: R2dbcConverter

    @Test
    fun convertNullValue() {
        databaseClient.sql(
            """
            SELECT CAST(null as text) AS field1,
                   '{"a": 1}'         AS field2
        """.trimIndent()
        )
            .map { row, metadata -> converter.read(MyRow::class.java, row, metadata) }
            .one()
            .test()
            .expectNext(MyRow(null, objectMapper.readTree("{\"a\": 1}")))
            .verifyComplete()
    }
}


data class MyRow(
    val field1: JsonNode?,
    val field2: JsonNode?
)
