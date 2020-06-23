package martintrollip.cats.app.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import martintrollip.cats.app.databinding.FragmentAboutBinding
import martintrollip.cats.app.utils.getViewModelFactory

/**
 * Display an about page
 */
class AboutFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentAboutBinding

    private val viewModel by viewModels<AboutViewModel>() {
        getViewModelFactory()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentAboutBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }
}
