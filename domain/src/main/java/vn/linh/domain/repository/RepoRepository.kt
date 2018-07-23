package vn.linh.domain.repository

import io.reactivex.Single
import vn.linh.domain.entity.Repo

interface RepoRepository : Repository {
    fun getListRepoGithub(name: String): Single<List<Repo>>
}