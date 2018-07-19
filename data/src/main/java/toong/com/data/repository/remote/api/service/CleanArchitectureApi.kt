package toong.com.data.repository.remote.api.service

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import toong.com.data.model.RepoData

interface CleanArchitectureApi {

    @GET("/users/{name}/repos")
    fun getGitRepos(@Path("name") name: String): Single<List<RepoData>>

}