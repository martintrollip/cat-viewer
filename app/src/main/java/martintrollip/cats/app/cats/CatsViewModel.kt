package martintrollip.cats.app.cats

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import martintrollip.cats.app.data.Result
import martintrollip.cats.app.data.model.Cat
import martintrollip.cats.app.data.source.CatsRepository

/**
 * ViewModel for the cats list screen.
 */
class CatsViewModel(private val catsRepository: CatsRepository) : ViewModel() {
    private val isDataLoadingError = MutableLiveData<Boolean>()
    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _forceUpdate = MutableLiveData<Boolean>(false)
    private val _items: LiveData<List<Cat>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                catsRepository.refreshCats()
                _dataLoading.value = false
            }
        }
        catsRepository.observeCats().switchMap { filterCats(it) }
    }

    val items: LiveData<List<Cat>> = _items

    private fun filterCats(catResult: Result<List<Cat>>): LiveData<List<Cat>> {
        val result = MutableLiveData<List<Cat>>()

        if (catResult is Result.Success) {
            isDataLoadingError.value = false
            viewModelScope.launch {
                result.value = catResult.data
            }
        } else {
            result.value = emptyList()
            isDataLoadingError.value = true
        }

        return result
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the [TasksDataSource]
     */
    fun loadCats(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

}
