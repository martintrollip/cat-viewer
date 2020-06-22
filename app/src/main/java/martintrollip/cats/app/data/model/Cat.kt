package martintrollip.cats.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Immutable Room model class for a Cat.
 *
 * @param id          id of the cat, random UUID by default
 * @param title       A random title (Eg, “Image 1”, “Image 2”, “Image 3” etc)
 * @param description A description using the title (Eg: “This is the description for {Title}, It's a really cool image, bask in it's gloriousness" etc) (Must be 50+ characters and include the Title)
 * @param url         The image url
 */
@Entity(tableName = "cats")
data class Cat @JvmOverloads constructor(
        @PrimaryKey @ColumnInfo(name = "catId") var id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "description") var description: String = "",
        @ColumnInfo(name = "url") var url: String = ""
) {
    val titleForList: String
        get() = title
}