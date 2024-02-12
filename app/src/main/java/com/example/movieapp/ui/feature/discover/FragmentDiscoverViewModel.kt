package com.example.movieapp.ui.feature.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.repository.RemoteRepository
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.state.RemoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentDiscoverViewModel @Inject constructor(private val remoteRepository: RemoteRepository) :
    ViewModel() {


    private val _movieStateFlow = MutableStateFlow<RemoteState<List<ResultsItem?>>>(RemoteState.Success(emptyList()))
    val movieStateFlow: StateFlow<RemoteState<List<ResultsItem?>>> get() = _movieStateFlow


    fun getBiggestRatingMovie(page: Int) {
        viewModelScope.launch {
            remoteRepository.getDiscoverRatingMovie(page)
                .collect { movies ->
                    _movieStateFlow.value = movies
                }

        }
    }

    init {
        getBiggestRatingMovie(page = 1)
    }

}