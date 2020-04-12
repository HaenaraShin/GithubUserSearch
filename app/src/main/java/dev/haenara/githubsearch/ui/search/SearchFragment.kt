package dev.haenara.githubsearch.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.haenara.githubsearch.R
import dev.haenara.githubsearch.model.User
import dev.haenara.githubsearch.repo.github.GithubUserRepo
import dev.haenara.githubsearch.repo.github.GithubUserRepoImpl
import dev.haenara.githubsearch.ui.UserListAdapter
import dev.haenara.githubsearch.ui.addScrollFinishedListener
import dev.haenara.githubsearch.ui.showIf
import kotlinx.android.synthetic.main.fragment_search.*
import android.net.Uri


class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        searchViewModel = SearchViewModelFactory(GithubUserRepoImpl())
            .create(SearchViewModel::class.java)
        searchViewModel.users.observe(this, Observer {data ->
            rv_user_list.adapter?.let{
                (it as UserListAdapter).users = data
                it.notifyDataSetChanged()
            }
            cl_empty.showIf(data.isEmpty())
        })
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onClickListener : (User)->Unit = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.html_url))
            startActivity(browserIntent)
        }
        rv_user_list.adapter = UserListAdapter(searchViewModel.users.value!!, onClickListener)
        rv_user_list.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
        rv_user_list.addScrollFinishedListener{ searchViewModel.onScrollFinished() }
        edt_search.initialize()
    }

    private fun EditText.initialize() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.trim()?.isNotEmpty() == true) {
                    val keyword = p0.trim().toString()
                    searchViewModel.getUserList(keyword)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        })
    }

    inner class SearchViewModelFactory(
        private val githubRepo : GithubUserRepo) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel(githubRepo) as T
        }
    }
}