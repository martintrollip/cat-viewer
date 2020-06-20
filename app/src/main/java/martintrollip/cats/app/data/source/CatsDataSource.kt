package martintrollip.cats.app.data.source

import androidx.lifecycle.LiveData
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.Result

/**
 * Main entry point for accessing cats data.
 */
interface CatsDataSource {

    fun observeCats(): LiveData<Result<List<Cat>>>

    suspend fun getCats(): Result<List<Cat>>

    suspend fun refreshCats()

    suspend fun deleteAllCats()
}
