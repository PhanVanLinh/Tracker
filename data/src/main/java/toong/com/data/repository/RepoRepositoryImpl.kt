package toong.com.data.repository

import io.reactivex.Single
import toong.com.data.repository.local.RepoLocalDatasource
import toong.com.data.repository.remote.api.RepoRemoteDataSource
import toong.com.domain.entity.Repo
import toong.com.domain.repository.RepoRepository

class RepoRepositoryImpl(private val localDataSource: RepoLocalDatasource,
                         private val remoteDataSource: RepoRemoteDataSource
) : RepoRepository {

    override fun getListRepoGithub(name: String): Single<List<Repo>> {
        return remoteDataSource.getGitRepos(name).map { it.map { it.mapToDomain() } }
    }
}