package dev.haenara.githubsearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import dev.haenara.githubsearch.R
import dev.haenara.githubsearch.model.User

class UserListAdapter(
    var users : ArrayList<User>,
    private val onClickListener: (User)->Unit) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.item_user, parent, false)
        return UserViewHolder(listItem)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position], onClickListener)
    }

}

class UserViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.tv_name)
    private val score = view.findViewById<TextView>(R.id.tv_score)
    private val avatar = view.findViewById<ImageView>(R.id.iv_profile)
    private val loading = view.findViewById<ProgressBar>(R.id.pb_loading)

    fun bind(user: User, onClickListener: (User) -> Unit) {
        name.text = user.login
        score.text = user.score
        loading.visibility = View.VISIBLE
        avatar.setImageFromUrl(view.context, user.avatar_url, loading)
        view.setOnClickListener { onClickListener(user) }
    }
}

