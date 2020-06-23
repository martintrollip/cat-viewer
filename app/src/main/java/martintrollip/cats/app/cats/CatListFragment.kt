package martintrollip.cats.app.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import martintrollip.cats.app.databinding.FragmentCatsBinding
import martintrollip.cats.app.utils.EventObserver
import martintrollip.cats.app.utils.getViewModelFactory
import timber.log.Timber

/**
 * Display a list of [Cat]s.
 */
class CatListFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentCatsBinding

    private lateinit var catsAdapter: CatsAdapter

    private val viewModel by viewModels<CatsViewModel>() {
        getViewModelFactory()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentCatsBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        viewModel.loadCats(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupClicks()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            catsAdapter = CatsAdapter(viewModel)
            viewDataBinding.catsList.adapter = catsAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupClicks() {
        viewModel.catDetailsEvent.observe(viewLifecycleOwner, EventObserver {
            launchCatDetails(it)
        })
    }

    private fun launchCatDetails(catId: String) {
        val action = CatListFragmentDirections.actionCatsFragmentToCatDetailsFragment(catId)
        findNavController().navigate(action)
    }
}
