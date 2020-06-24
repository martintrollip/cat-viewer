package martintrollip.cats.app.cats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import martintrollip.cats.app.FakeCatsRepository
import martintrollip.cats.app.MainCoroutineRule
import martintrollip.cats.app.data.model.Cat
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the localhost.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class CatsViewModelTest {
    private lateinit var catsRepository: FakeCatsRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantCatExecutorRule = InstantTaskExecutorRule() //Runs all architecture components background cats on the same thread. Add this when testing LiveData

    // Subject under test
    private lateinit var catViewModel: CatsViewModel

    @Before
    fun setupViewModel() {
        catsRepository = FakeCatsRepository()

        val cat1 = Cat("id1", "TITLE1", "DESCRIPTION1", "https://25.media.tumblr.com/tumblr_m4gjqmNI3m1qhwmnpo1_500.jpg")
        val cat2 = Cat("id2", "TITLE2", "DESCRIPTION2", "https://cdn2.thecatapi.com/images/7v6.gif")
        val cat3 = Cat("id2", "TITLE3", "DESCRIPTION3", "https://25.media.tumblr.com/tumblr_m4gjqmNI3m1qhwmnpo1_500.jpg")
        catsRepository.addCats(cat1, cat2, cat3)

        catViewModel = CatsViewModel(catsRepository) // You want to initialse catViewModel here to make sure each test run has it's own instance
    }

    @Before
    fun setupStatisticsViewModel() {
        // Initialise the repository with no tasks.
        catsRepository = FakeCatsRepository()
        catViewModel = CatsViewModel(catsRepository)
    }

    @Test
    fun loadCats_loading() {
        //GIVEN initial view model
        mainCoroutineRule.pauseDispatcher() //We pause coroutines and "manually resume", this gives the opportunity to assert all the states we're interested in

        //WHEN loading
        catViewModel.loadCats(true)

        //THEN the view model has a loading state
        MatcherAssert.assertThat(catViewModel.dataLoading.getOrAwaitValue(), CoreMatchers.`is`(true))

        //AND Some time later we want that spinner to gbe dismissed
        mainCoroutineRule.resumeDispatcher()
        MatcherAssert.assertThat(catViewModel.dataLoading.getOrAwaitValue(), CoreMatchers.`is`(false))
    }
}
