package toong.com.data

import dagger.Module
import dagger.Provides
import toong.com.data.repository.RepoRepositoryImpl
import toong.com.data.repository.local.RepoLocalDatasource
import toong.com.data.repository.remote.api.RepoRemoteDataSource
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