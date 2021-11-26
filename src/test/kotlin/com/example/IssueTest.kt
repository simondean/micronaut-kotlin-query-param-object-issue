package com.example

import com.example.services.FakeExampleService
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import javax.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

@MicronautTest
class IssueTest {

    val micronautVersion = System.getenv("MICRONAUT_VERSION")
    val micronautMajorVersion = micronautVersion.substringBefore(".").toInt()

    @Inject
    lateinit var fakeExampleService: FakeExampleService
    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @MockBean(FakeExampleService::class)
    fun exampleService() = FakeExampleService()

    @Test
    fun computedPropertiesShouldBeSupportedOnQueryValueObjects() {

        val request = GET<Unit>("/examples/search?propertyWithNullDefault=hello+world")
        val response = client
            .toBlocking()
            .exchange(request, Unit.javaClass)

        assertThat(response.code()).isEqualTo(201)

        val queries = fakeExampleService.queries
        assertThat(queries).hasSize(1)
        val query = queries[0]

        assertThat(query.propertyWithNullDefault)
            .isEqualTo("hello world")

        assertThat(query)
            .extracting { it.computedProperty }
            .isEqualTo(when (micronautMajorVersion) {
                2 -> "HELLO WORLD"
                3 -> null // This should be "HELLO WORLD" and not null
                else -> fail("Unexpected Micronaut major version $micronautVersion")
            })
    }

}
