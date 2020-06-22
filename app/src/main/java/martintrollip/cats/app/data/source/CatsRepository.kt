package martintrollip.cats.app.data.source

import androidx.lifecycle.LiveData
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.Result

interface CatsRepository {

    suspend fun getCats(forceUpdate: Boolean = false): Result<List<Cat>>

    fun observeCats(): LiveData<Result<List<Cat>>>

    fun observeCat(catId: String): LiveData<Result<Cat>>

    suspend fun refreshCats()

    /**
     * Relies on [getCats] to fetch data and picks the cat with the same ID.
     */
    suspend fun getCat(catId: String, forceUpdate: Boolean = false): Result<Cat>

    suspend fun saveCat(cat: Cat)

}