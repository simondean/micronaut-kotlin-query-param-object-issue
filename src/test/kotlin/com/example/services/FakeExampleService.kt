package com.example.services

import com.example.models.ExampleQuery

open class FakeExampleService : ExampleService {

    private val mutableQueries = mutableListOf<ExampleQuery>()
    val queries: List<ExampleQuery> = mutableQueries

    override fun search(query: ExampleQuery) {
        mutableQueries.add(query)
    }

}