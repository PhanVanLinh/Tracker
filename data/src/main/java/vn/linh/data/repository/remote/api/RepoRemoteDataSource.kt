package vn.linh.data.repository.remote.api

import io.reactivex.Single
import vn.linh.data.model.RepoData
import vn.linh.data.repository.remote.api.service.CleanArchitectureApi
import javax.inject.Inject

class RepoRemoteDataSource @Inject constructor(private val cleanArchitectureApi: CleanArchitectureApi) {

    fun getGitRepos(name: String): Single<List<RepoData>> {
        return cleanArchitectureApi.getGitRepos("")
    }
}