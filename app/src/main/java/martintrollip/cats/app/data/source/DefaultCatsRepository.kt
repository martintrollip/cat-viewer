package martintrollip.cats.app.data.source

import androidx.lifecycle.LiveData
import martintrollip.cats.app.data.Result
import martintrollip.cats.app.data.model.Cat

/**
 * Concrete implementation to load cats from the data sources into a cache.
 */
class DefaultCatsRepository(
        private val catsRemoteDataSource: CatsDataSource,
        private val catsLocalDataSource: CatsDataSource) : CatsRepository {

    override suspend fun getCats(forceUpdate: Boolean): Result<List<Cat>> {
        if (forceUpdate) {
            try {
                getCatsFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return catsLocalDataSource.getCats()
    }

    private suspend fun getCatsFromRemoteDataSource() {
        val remoteCats = catsRemoteDataSource.getCats()

        if (remoteCats is Result.Success) {
            catsLocalDataSource.deleteAllCats()
            remoteCats.data.forEach { cat ->
                catsLocalDataSource.save(cat)
            }
        } else if (remoteCats is Result.Error) {
//            throw remoteCats.exception
        }
    }

    override suspend fun refreshCats() {
        getCatsFromRemoteDataSource()
    }

    override fun observeCats(): LiveData<Result<List<Cat>>> {
        return catsLocalDataSource.observeCats()
    }

    override fun observeCat(catId: String): LiveData<Result<Cat>> {
        return catsLocalDataSource.observeCat(catId)
    }

    override suspend fun getCat(catId: String, forceUpdate: Boolean): Result<Cat> {
        return catsLocalDataSource.getCat(catId)
    }

    override suspend fun saveCat(cat: Cat) {
        return catsLocalDataSource.save(cat)
    }
}