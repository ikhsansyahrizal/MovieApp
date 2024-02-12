package com.example.movieapp.ui.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.docotel.ikhsansyahrizal.doconewss.helper.EndlessOnScrollListener
import com.example.movieapp.databinding.FragmentFragmentHomeBinding
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.state.RemoteState
import com.example.movieapp.ui.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : Fragment() {

    private var _binding: FragmentFragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragmentHomeViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    private var page = 1
    private var loadMore = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter()
        binding.rvMovie.adapter = movieAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieStateFlow.collect() { movieState ->
                    when(movieState) {
                        is RemoteState.Success -> updateUI(movieState.data)
                        is RemoteState.Error -> Toast.makeText(requireContext(), "Failed fetch data", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        scrollData().let { newItemDetected ->
            binding.rvMovie.addOnScrollListener(newItemDetected)
        }
    }

    private fun updateUI(movies: List<ResultsItem?>) {
        movieAdapter.setList(movies)
    }


    private fun scrollData(): EndlessOnScrollListener {
        return object : EndlessOnScrollListener() {
            override fun onLoadMore() {
                page += 1
                loadMore = true
                viewModel.getUpdatedNews(page)
            }
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}