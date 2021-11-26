package com.example.models

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.annotation.QueryValue

@Introspected
data class ExampleQuery(
    @field:QueryValue val propertyWithNullDefault: String? = null,
) {

    val computedProperty = propertyWithNullDefault?.toUpperCase()

}
