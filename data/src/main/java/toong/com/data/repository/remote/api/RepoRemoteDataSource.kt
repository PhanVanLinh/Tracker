package toong.com.data.repository.remote.api

import io.reactivex.Single
import toong.com.data.model.RepoData
import toong.com.data.repository.remote.api.service.CleanArchitectureApi
import javax.inject.Inject

class RepoRemoteDataSource @Inject constructor(private val cleanArchitectureApi: CleanArchitectureApi) {

    fun getGitRepos(name: String): Single<List<RepoData>> {
        return cleanArchitectureApi.getGitRepos("")
    }
}