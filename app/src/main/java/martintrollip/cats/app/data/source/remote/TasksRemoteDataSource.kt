package martintrollip.cats.app.data.source.remote

import androidx.lifecycle.LiveData
import martintrollip.cats.app.data.Result
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.source.CatsDataSource
import timber.log.Timber

/**
 * Implementation of the data source that adds a latency simulating network.
 */
object CatsRemoteDataSource : CatsDataSource {

    override fun observeCats(): LiveData<Result<List<Cat>>> {
        throw NotImplementedError("CatsRemoteDataSource.observeCats should not be required")
    }

    override fun observeCat(catId: String): LiveData<Result<Cat>> {
        throw NotImplementedError("CatsRemoteDataSource.observeCats should not be required")
    }

    override suspend fun getCat(catId: String): Result<Cat> {
        throw NotImplementedError("CatsRemoteDataSource.getCat should not be required")
    }

    override suspend fun getCats(): Result<List<Cat>> {
        try {
            val response = CatsApiService.catsApi.getImages().await()
            Timber.d(response.body().toString())
            if (response.isSuccessful) {
                val cats = response.body()!!
                cats.forEachIndexed { i, it ->
                    it.title = "Cat $i"
                    it.description = "This is the description for ${it.title}, It's a really cool image, bask in it's gloriousness and magnificence"
                }
                return Result.Success(cats)
            }
        } catch (ex: Exception) {
            return Result.Error(ex)
        }
        return Result.Success(ArrayList())
    }

    override suspend fun save(cat: Cat) {
        throw NotImplementedError("CatsRemoteDataSource.save should not be required")
    }

    override suspend fun deleteAllCats() {
        throw NotImplementedError("CatsRemoteDataSource.deleteAllCats should not be required")
    }
}
