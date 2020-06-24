package martintrollip.cats.app

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import martintrollip.cats.app.cats.CatListFragment
import martintrollip.cats.app.cats.CatListFragmentDirections
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.source.CatsRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * androidTests executed on device using mockito and espresso. Turn off animations to increase performance
 *
 * @author Martin Trollip
 * @since 2020/06/24 05:59
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class CatsListFragmentTest {

    private lateinit var repository: CatsRepository

    @Before
    fun initRepository() {
        repository = FakeCatsRepository()
        ServiceLocator.catsRepository = repository
    }

    @Test
    fun clickCat_navigateToDetailFragment() = runBlockingTest {
        // GIVEN - On the home screen with two cats in the list
        repository.saveCat(Cat("id1", "TITLE1", "DESCRIPTION1", "https://25.media.tumblr.com/tumblr_m4gjqmNI3m1qhwmnpo1_500.jpg"))
        repository.saveCat(Cat("id2", "TITLE2", "DESCRIPTION2", "https://cdn2.thecatapi.com/images/7v6.gif"))

        val scenario = launchFragmentInContainer<CatListFragment>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController) // The fragment is now using the mock nav controller
        }

        //WHEN - click on the first list item
        onView(withId(R.id.cats_list))
                .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                        hasDescendant(withText("TITLE1")), click()))


        //THEN - Verify that we navigate to the  detail screen
        verify(navController).navigate(CatListFragmentDirections.actionCatsFragmentToCatDetailsFragment("id1"))
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.resetRepository()
    }

}