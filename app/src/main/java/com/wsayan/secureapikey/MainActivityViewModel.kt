package com.wsayan.secureapikey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsayan.secureapikey.domain.ResourceState
import com.wsayan.secureapikey.domain.model.AnimeItem
import com.wsayan.secureapikey.domain.usecase.GetAnimeListUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainActivityViewModel(private val getAnimeListUseCase: GetAnimeListUseCase) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = getAnimeListUseCase().map {
        when (it) {
            is ResourceState.Error -> {
                MainActivityUiState.Error(it.message)
            }

            is ResourceState.Loading -> {
                MainActivityUiState.Loading
            }

            is ResourceState.Success -> {
                MainActivityUiState.Success(it.data)
            }
        }

    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState

    data class Success(val animeList: List<AnimeItem>) :
        MainActivityUiState

    data class Error(val message: String) : MainActivityUiState

    fun shouldKeepSplashScreen() = this is Loading
}