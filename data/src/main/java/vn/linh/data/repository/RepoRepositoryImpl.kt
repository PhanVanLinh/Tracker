package vn.linh.data.repository

import io.reactivex.Single
import vn.linh.data.repository.local.RepoLocalDatasource
import vn.linh.data.repository.remote.api.RepoRemoteDataSource
import vn.linh.domain.entity.Repo
import vn.linh.domain.repository.RepoRepository

class RepoRepositoryImpl(private val localDataSource: RepoLocalDatasource,
                         private val remoteDataSource: RepoRemoteDataSource
) : RepoRepository {

    override fun getListRepoGithub(name: String): Single<List<Repo>> {
        return remoteDataSource.getGitRepos(name).map { it.map { it.mapToDomain() } }
    }
}