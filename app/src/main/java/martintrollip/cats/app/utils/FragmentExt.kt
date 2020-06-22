package martintrollip.cats.app.utils

/**
 * Extension functions for Fragment.
 */

import androidx.fragment.app.Fragment
import martintrollip.cats.app.CatsApplication

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as CatsApplication).catsRepository
    return ViewModelFactory(repository)
}