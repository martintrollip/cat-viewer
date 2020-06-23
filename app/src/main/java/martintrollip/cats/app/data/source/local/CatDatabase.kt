package martintrollip.cats.app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import martintrollip.cats.app.data.model.Cat

/**
 * The Room Database that contains the Cat table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Cat::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {

    abstract fun catDao(): CatsDao
}
