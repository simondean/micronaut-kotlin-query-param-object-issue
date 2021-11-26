package com.example.controllers

import com.example.models.ExampleQuery
import com.example.services.ExampleService
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import kotlinx.coroutines.coroutineScope

@Controller("/examples")
class ExampleController(
    val exampleService: ExampleService
) {

    @Get("/search{?query*}")
    suspend fun search(
        @QueryValue("query") query: ExampleQuery
    ): HttpStatus = coroutineScope {
        exampleService.search(query)
        HttpStatus.CREATED
    }

}