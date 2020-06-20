package martintrollip.cats.app.data.source

import androidx.lifecycle.LiveData
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.Result

/**
 * @author Martin Trollip
 * @since 2020/03/16 19:24
 */
interface CatsRepository {
    suspend fun getCats(forceUpdate: Boolean = false): Result<List<Cat>>

    suspend fun refreshCats()
    fun observeCats(): LiveData<Result<List<Cat>>>

    suspend fun refreshCat(catId: String)

    fun observeCat(catId: String): LiveData<Result<Cat>>

    /**
     * Relies on [getCats] to fetch data and picks the cat with the same ID.
     */
    suspend fun getCat(catId: String, forceUpdate: Boolean = false): Result<Cat>

    suspend fun saveCat(cat: Cat)


}