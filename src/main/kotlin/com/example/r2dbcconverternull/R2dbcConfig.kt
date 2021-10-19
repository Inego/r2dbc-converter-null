package com.example.r2dbcconverternull

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class R2dbcConfig(
    private val connectionFactory: ConnectionFactory
) : AbstractR2dbcConfiguration() {

    override fun connectionFactory() = connectionFactory

    override fun getCustomConverters() = listOf(StringToJsonNodeConverter)
}


object StringToJsonNodeConverter: Converter<String, JsonNode> {
    private val objectMapper = ObjectMapper()
    override fun convert(source: String): JsonNode {
        return objectMapper.readTree(source)
    }
}