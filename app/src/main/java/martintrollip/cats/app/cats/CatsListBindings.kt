package martintrollip.cats.app.cats

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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