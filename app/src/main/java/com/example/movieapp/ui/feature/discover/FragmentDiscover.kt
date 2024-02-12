package com.example.movieapp.ui.feature.discover

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
import androidx.viewpager2.widget.ViewPager2
import com.docotel.ikhsansyahrizal.doconewss.helper.EndlessOnScrollListener
import com.example.movieapp.databinding.FragmentFragmentDiscoverBinding
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.state.RemoteState
import com.example.movieapp.ui.adapter.MovieAdapter
import com.example.movieapp.ui.adapter.OneMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentDiscover : Fragment() {

    private var _binding: FragmentFragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragmentDiscoverViewModel by viewModels()
    private lateinit var OneMovieAdapter: OneMovieAdapter

    private var page = 1
    private var loadMore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        OneMovieAdapter = OneMovieAdapter()

        binding.viewPager2BestRatingMovie.apply {
            adapter = OneMovieAdapter
            offscreenPageLimit = 3
            setPageTransformer(SliderTransformer(3))
        }
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
            binding.viewPager2BestRatingMovie.registerOnPageChangeCallback(newItemDetected)
        }
    }

    private fun updateUI(movies: List<ResultsItem?>) {
        OneMovieAdapter.setList(movies)
    }

    private fun scrollData(): ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == OneMovieAdapter.itemCount - 1) {
                    // Reached end of ViewPager, load more data
                    page++
                    loadMore = true
                    viewModel.getBiggestRatingMovie(page)
                }
            }
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}