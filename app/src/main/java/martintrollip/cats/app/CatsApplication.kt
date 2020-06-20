package martintrollip.cats.app

import android.app.Application
import martintrollip.cats.app.data.source.CatsRepository
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * An application that lazily provides a repository.
 *
 * Also, sets up Timber in the DEBUG BuildConfig. Read Timber's documentation for production setups.
 */
class CatsApplication : Application() {

    val catsRepository: CatsRepository get() = ServiceLocator.provideCatsRepository(this)

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
