package martintrollip.cats.app.details

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import martintrollip.cats.app.R
import martintrollip.cats.app.databinding.FragmentDetailBinding
import martintrollip.cats.app.utils.EventObserver
import martintrollip.cats.app.utils.getViewModelFactory

/**
 * Display a Cat detail page
 */
class DetailFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentDetailBinding

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var exoPlayer: SimpleExoPlayer

    private val viewModel by viewModels<DetailViewModel> {
        getViewModelFactory()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        viewDataBinding = FragmentDetailBinding.bind(view).apply {
            viewmodel = viewModel
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.start(args.catId)

        setHasOptionsMenu(true)

        setupMeows()

        return view
    }

    private fun setupMeows() {
        viewModel.catMeowEvent.observe(viewLifecycleOwner, EventObserver {
            meow()
        })
    }

    //https://github.com/CasterIO/ExoPlayerIntro/blob/lesson/setting_up_exoplayer/app/src/main/kotlin/io/caster/exoplayerintro/MainActivity.kt
    private fun meow() {
        val trackSelector = DefaultTrackSelector()
        exoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext(), trackSelector)

        val userAgent = Util.getUserAgent(requireContext(), "ExoPlayerIntro")
        val mediaSource = ExtractorMediaSource(
                Uri.parse("asset:///meow.mp3"),
                DefaultDataSourceFactory(requireContext(), userAgent),
                DefaultExtractorsFactory(),
                null, // eventHandler: Handler
                null) // eventListener: ExtractorMediaSource.EventListener

        exoPlayer.prepare(mediaSource)
        exoPlayer.playWhenReady = true
    }

    private fun releaseExoplayer() = exoPlayer.release()

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            releaseExoplayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            releaseExoplayer()
        }
    }
}
