package dev.haenara.githubsearch.repo.github

import dev.haenara.githubsearch.model.User

/**
 * Github 사용자 목록에서 이름을 검색하여 가져오는 Repo
 * API 를 통해 Github의 사용자 목록을 가져올 수도 있고
 * Local DB에 저장된 사용자 목록을 가져올 수도 있다.
 */
interface GithubUserRepo {
    fun getUserList(name : String,
                    page : Int,
                    success : (List<User>)-> Unit,
                    error : (String)-> Unit)
}

