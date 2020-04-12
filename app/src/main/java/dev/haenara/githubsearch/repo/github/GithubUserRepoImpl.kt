package dev.haenara.githubsearch.repo.github

import dev.haenara.githubsearch.api.GitHubSearchRetrofit
import dev.haenara.githubsearch.model.User
import dev.haenara.githubsearch.model.UserList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserRepoImpl : GithubUserRepo {
    override fun getUserList(
        name: String,
        page: Int,
        success: (List<User>) -> Unit,
        error: (String) -> Unit
    ) {
        GitHubSearchRetrofit.create().searchUsers("$name+in:login", page).enqueue(
            object : Callback<UserList> {
                override fun onFailure(call: Call<UserList>, t: Throwable) {
                    t.printStackTrace()
                    error(t.localizedMessage ?: "")
                }

                override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                    if (response.isSuccessful && response.body()?.items != null) {
                        success(response.body()?.items!!)
                    }
                }
            }
        )
    }
}