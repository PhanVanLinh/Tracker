package vn.linh.data

import dagger.Module
import dagger.Provides
import vn.linh.data.repository.RepoRepositoryImpl
import vn.linh.data.repository.local.RepoLocalDatasource
import vn.linh.data.repository.remote.api.RepoRemoteDataSource
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepoRepo(remoteDataSource: RepoRemoteDataSource,
                        localDataSource: RepoLocalDatasource): RepoRepositoryImpl {
        return RepoRepositoryImpl(localDataSource,
                remoteDataSource)
    }
//
//    @Singleton
//    @Provides
//    fun provideDatabaseApi(databaseManager: DatabaseManager): DatabaseApi {
//        return DatabaseApiImpl(databaseManager)
//    }
//
//    @Singleton
//    @Provides
//    fun provideDatabaseManager(context: Context): DatabaseManager {
//        return Room.databaseBuilder(context, DatabaseManager::class.java,
//                DatabaseManager.DATABASE_NAME)
//                .build()
//    }
}