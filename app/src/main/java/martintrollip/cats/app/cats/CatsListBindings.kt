package martintrollip.cats.app.cats

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import martintrollip.cats.app.R
import martintrollip.cats.app.data.model.Cat

/**
 * [BindingAdapter]s for the [Cat]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Cat>?) {
    items?.let {
        (listView.adapter as CatsAdapter).submitList(items)
    }
}

@BindingAdapter("bind:imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_cat)
            .into(view)
}