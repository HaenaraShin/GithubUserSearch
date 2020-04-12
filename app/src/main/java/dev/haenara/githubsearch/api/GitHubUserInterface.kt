package dev.haenara.githubsearch.api

import dev.haenara.githubsearch.model.UserList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubUserInterface {
    //  예시 : https://api.github.com/search/users?q=haenara+in:login&page=0&per_page=100

    @Headers(AUTH_HEADER)
    @GET("/search/users")
    fun searchUsers(@Query(value = "q", encoded = true) id : String,
                    @Query("page") page : Int = 1,
                    @Query("per_page") perPage : Int = PER_PAGE) : Call<UserList>
}