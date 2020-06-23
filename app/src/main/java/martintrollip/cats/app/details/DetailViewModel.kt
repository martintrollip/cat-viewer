package martintrollip.cats.app.details

import androidx.lifecycle.*
import martintrollip.cats.app.data.Result
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.source.CatsRepository
import martintrollip.cats.app.utils.Event


/**
 * ViewModel for the cats list screen.
 */
class DetailViewModel(private val catsRepository: CatsRepository) : ViewModel() {
    private val _catId = MutableLiveData<String>()

    private val _cat = _catId.switchMap { catId ->
        catsRepository.observeCat(catId).map { computeResult(it) }
    }
    val cat: LiveData<Cat?> = _cat

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading


    private fun computeResult(catResult: Result<Cat>): Cat? {
        return if (catResult is Result.Success) {
            catResult.data
        } else {
            //Ignore
            null
        }
    }

    fun start(catId: String?) {
        if (_dataLoading.value == true || catId == _catId.value) {
            return
        }
        _catId.value = catId
    }

    private val _catMeowEvent = MutableLiveData<Event<String>>()
    val catMeowEvent: LiveData<Event<String>> = _catMeowEvent


    /**
     * Meow, are you sure you want to click on this
     */
    fun meow() {
        _catMeowEvent.value = Event("meow")
    }
}
