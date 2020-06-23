package martintrollip.cats.app.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import martintrollip.cats.app.data.source.CatsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.Result

/**
 * Concrete implementation of a data source as a db.
 */
class CatsLocalDataSource internal constructor(
        private val catsDao: CatsDao,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CatsDataSource {

    override fun observeCats(): LiveData<Result<List<Cat>>> {
        return catsDao.observeCats().map {
            Result.Success(it)
        }
    }

    override fun observeCat(catId: String): LiveData<Result<Cat>> {
        return catsDao.observeCat(catId).map {
            Result.Success(it)
        }
    }

    override suspend fun getCats(): Result<List<Cat>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(catsDao.getCats())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCat(catId: String): Result<Cat> = withContext(ioDispatcher) {
        try {
            val task = catsDao.getCat(catId)
            if (task != null) {
                return@withContext Result.Success(task)
            } else {
                return@withContext Result.Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun save(cat: Cat) = withContext(ioDispatcher) {
        catsDao.insertCat(cat)
    }

    override suspend fun deleteAllCats() = withContext(ioDispatcher) {
        catsDao.deleteAll()
    }
}
