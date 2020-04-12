package dev.haenara.githubsearch.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.haenara.githubsearch.api.LOG_TAG
import dev.haenara.githubsearch.model.User
import dev.haenara.githubsearch.repo.github.GithubUserRepo

class SearchViewModel(
    private val userRepo : GithubUserRepo
) : ViewModel() {
    private val _users = MutableLiveData<ArrayList<User>>().apply{
        value = arrayListOf()
    }
    val users : LiveData<ArrayList<User>> get() = _users

    private var lastKeyword = ""
    private var pageCount = 1
    fun getUserList(toSearch: String) {
        val baseList = if (toSearch != lastKeyword) {
            lastKeyword = toSearch
            pageCount = 1
            arrayListOf()
        } else {
            pageCount++
            _users.value
        }
        userRepo.getUserList(toSearch, pageCount, success = {
            it.forEach { user ->
                baseList?.add(user)
            }
            _users.value = baseList
        }, error = {
            Log.e(LOG_TAG, "Error occurred : $it")
        })
    }

    fun onScrollFinished() {
        getUserList(lastKeyword)
    }
}