package dev.haenara.githubsearch.repo.github

import dev.haenara.githubsearch.api.GitHubSearchRetrofit
import dev.haenara.githubsearch.model.User
import dev.haenara.githubsearch.model.UserList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserRepoImpl : GithubUserRepo {
    private val cached = hashMapOf<Pair<String, Int>, List<User>>()
    override fun getUserList(
        name: String,
        page: Int,
        success: (List<User>) -> Unit,
        error: (String) -> Unit
    ) {
        if (isCached(name, page)) {
            getFromCache(name, page, success, error)
        } else {
            getFromApi(name, page, success, error)
        }
    }

    private fun getFromApi(name: String,
                           page: Int,
                           success: (List<User>) -> Unit,
                           error: (String) -> Unit) {
        GitHubSearchRetrofit.create().searchUsers("$name+in:login", page).enqueue(
            object : Callback<UserList> {
                override fun onFailure(call: Call<UserList>, t: Throwable) {
                    t.printStackTrace()
                    error(t.localizedMessage ?: "")
                }

                override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                    if (response.isSuccessful && response.body()?.items != null) {
                        caching(name, page, response.body()?.items!!)
                        success(response.body()?.items!!)
                    }
                }
            }
        )

    }

    private fun caching(name: String, page: Int, items: List<User>) {
        cached[Pair(name, page)] = items
    }

    private fun getFromCache(name: String,
                             page: Int,
                             success: (List<User>) -> Unit,
                             error: (String) -> Unit) {
        try {
            success(cached[Pair(name, page)]!!)
        } catch (e : NoSuchElementException) {
            error(e.localizedMessage ?: "")
        }
    }

    private fun isCached(name : String, page : Int) = cached.containsKey(Pair(name, page))

}