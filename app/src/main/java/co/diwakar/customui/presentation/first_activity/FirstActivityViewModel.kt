package co.diwakar.customui.presentation.first_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.diwakar.customui.domain.repository.AppRepository
import co.diwakar.customui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstActivityViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FirstActivityState())
    val state = _state as StateFlow<FirstActivityState>

    fun getCustomUi() {
        viewModelScope.launch {
            appRepository.getCustomUI()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(customUI = result.data)
                            }
                        }
                        is Resource.Error -> {
                            _state.update {
                                it.copy(errorMessage = result.message)
                            }
                        }
                        is Resource.Loading -> {
                            _state.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }
                    }
                }
        }
    }
}