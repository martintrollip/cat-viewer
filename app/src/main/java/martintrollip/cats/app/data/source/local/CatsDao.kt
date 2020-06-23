package martintrollip.cats.app.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import martintrollip.cats.app.data.model.Cat

/**
 * Data Access Object for the cats table.
 *
 * Room turns this into classes via annotation processing
 */
@Dao
interface CatsDao {

    /**
     * Observes list of cats.
     *
     * @return all cats.
     */
    @Query("SELECT * FROM Cats")
    fun observeCats(): LiveData<List<Cat>>

    /**
     * Observes a cat.
     *
     * @return all cats.
     */
    @Query("SELECT * FROM Cats WHERE catId = :catId")
    fun observeCat(catId: String): LiveData<Cat>

    /**
     * Select all cats from the cats table.
     *
     * @return all cats.
     */
    @Query("SELECT * FROM Cats")
    suspend fun getCats(): List<Cat>

    /**
     * Select a cat by id.
     *
     * @param catId the cat id.
     * @return the cat with catId.
     */
    @Query("SELECT * FROM Cats WHERE catId = :catId")
    suspend fun getCat(catId: String): Cat?

    /**
     * Insert a cat in the database. If the cat already exists, replace it.
     *
     * @param cat the cat to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCat(cat: Cat)

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM cats")
    suspend fun deleteAll() {

    }
}
