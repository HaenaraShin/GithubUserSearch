package dev.haenara.githubsearch.model

data class User(
    val login : String,
    val avatar_url : String,
    val score : String,
    val html_url : String) {

    override fun equals(other: Any?): Boolean {
        return if (other is User?) {
            other?.login == login
        } else {
            false
        }
    }
}