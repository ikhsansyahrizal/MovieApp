package com.example.movieapp.ui.feature.colllection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.repository.RemoteRepository
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.state.RemoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentCollectionViewModel @Inject constructor(private val remoteRepository: RemoteRepository) :
    ViewModel() {


    private val _horrorStateFlow = MutableStateFlow<RemoteState<List<ResultsItem?>>>(RemoteState.Success(emptyList()))
    val horrorMovie: StateFlow<RemoteState<List<ResultsItem?>>> = _horrorStateFlow

    private val _actionStateFlow = MutableStateFlow<RemoteState<List<ResultsItem?>>>(RemoteState.Success(emptyList()))
    val actionMovie: StateFlow<RemoteState<List<ResultsItem?>>> = _actionStateFlow

    fun getHorrorMovie(page:Int, genre: Int) {
        viewModelScope.launch {
            remoteRepository.getMovieByGenre(page, genre)
                .collect { movies ->
                    _horrorStateFlow.value = movies
                }
        }
    }

    fun getActionMovie(page:Int, genre: Int) {
        viewModelScope.launch {
            remoteRepository.getMovieByGenre(page, genre)
                .collect { movies ->
                    _actionStateFlow.value = movies
                }
        }
    }

    init {
        getHorrorMovie(1, 27)
        getActionMovie(1, 28)
    }

}