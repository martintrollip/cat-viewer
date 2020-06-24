package martintrollip.cats.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.runBlocking
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.source.CatsRepository
import martintrollip.cats.app.data.Result
import java.util.*

/**
 * This class is a fake an it allows us to test the entire view model, without us having to worry about its dependencies.
 *
 * The Fake class and the real class will share a common interface.
 *
 * @author Martin Trollip
 * @since 2020/03/16 19:22
 */
class FakeCatsRepository : CatsRepository {
    var catsServiceData: LinkedHashMap<String, Cat> = LinkedHashMap()
    private val observableCats = MutableLiveData<Result<List<Cat>>>()
    private var shouldReturnError = false

    fun addCats(vararg cats: Cat) {
        for (cat in cats) {
            catsServiceData[cat.id] = cat
        }
        runBlocking { refreshCats() }
    }

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun refreshCats() {
        observableCats.value = getCats()
    }

    override suspend fun refreshCat(catId: String) {
        refreshCats()
    }

    override fun observeCats(): LiveData<Result<List<Cat>>> {
        runBlocking { refreshCats() }
        return observableCats
    }

    override fun observeCat(catId: String): LiveData<Result<Cat>> {
        runBlocking { refreshCats() }
        return observableCats.map { cats ->
            when (cats) {
                is Result.Success -> {
                    val cat = cats.data.first { it.id == catId }
                    Result.Success(cat)
                }
                else -> Result.Success(Cat())
            }
        }
    }

    override suspend fun getCat(catId: String, forceUpdate: Boolean): Result<Cat> {
        if (shouldReturnError) {
            return Result.Error(Exception("Test exception"))
        }
        catsServiceData[catId]?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Could not find cat"))
    }

    override suspend fun getCats(forceUpdate: Boolean): Result<List<Cat>> {
        if (shouldReturnError) {
            return Result.Error(Exception("Test exception"))
        }
        return Result.Success(catsServiceData.values.toList())
    }

    override suspend fun saveCat(cat: Cat) {
        catsServiceData[cat.id] = cat
    }
}