package toong.com.domain.repository

import io.reactivex.Single
import toong.com.domain.entity.Repo

interface RepoRepository : Repository {
    fun getListRepoGithub(name: String): Single<List<Repo>>
}