package dev.haenara.githubsearch.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GitHubSearchRetrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl(SEARCH_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create() = retrofit.create(GitHubUserInterface::class.java)
}