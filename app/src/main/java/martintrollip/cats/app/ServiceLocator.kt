package martintrollip.cats.app

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import kotlinx.coroutines.runBlocking
import martintrollip.cats.app.data.source.CatsDataSource
import martintrollip.cats.app.data.source.CatsRepository
import martintrollip.cats.app.data.source.DefaultCatsRepository
import martintrollip.cats.app.data.source.local.CatDatabase
import martintrollip.cats.app.data.source.local.CatsLocalDataSource
import martintrollip.cats.app.data.source.remote.CatsRemoteDataSource

/**
 * A Service locator to simplify the small app.  A Dependency Injection framework can be implemented instead of this.
 */
object ServiceLocator {

    private var database: CatDatabase? = null

    @Volatile
    var catsRepository: CatsRepository? = null
        @VisibleForTesting set //The setter of catsRepository is visible in testing cases

    //Either provides an already existing repository or creates a new one.
    fun provideCatsRepository(context: Context): CatsRepository {
        synchronized(this) {
            return catsRepository ?: createCatsRepository(context)
        }
    }

    //Code for creating a new repository. Will call createCatLocalDataSource and create a new CatsRemoteDataSource.
    private fun createCatsRepository(context: Context): CatsRepository {
        val newRepo = DefaultCatsRepository(CatsRemoteDataSource, createCatLocalDataSource(context))
        catsRepository = newRepo
        return newRepo
    }

    //createCatLocalDataSource - Code for creating a new local data source. Will call createDataBase.
    private fun createCatLocalDataSource(context: Context): CatsDataSource {
        val database = database ?: createDataBase(context)
        return CatsLocalDataSource(database.catDao())
    }

    //createDataBase - Code for creating a new database.
    private fun createDataBase(context: Context): CatDatabase {
        val result = Room.databaseBuilder(
                context.applicationContext,
                CatDatabase::class.java, "Cats.db"
        ).build()
        database = result
        return result
    }

    private val lock = Any()
    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            runBlocking {
                CatsRemoteDataSource.deleteAllCats()
            }
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            catsRepository = null
        }
    }
}