package com.example.movieapp.ui.feature.colllection

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
import com.example.movieapp.databinding.FragmentFragmentCollectionBinding
import com.example.movieapp.model.ResultsItem
import com.example.movieapp.state.RemoteState
import com.example.movieapp.ui.adapter.CardMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentCollection : Fragment() {

    private var _binding: FragmentFragmentCollectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FragmentCollectionViewModel by viewModels()
    private lateinit var CardMovieAdapter: CardMovieAdapter
    private lateinit var ActionMovieAdapter: CardMovieAdapter

    private var page = 1
    private var loadMore = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFragmentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CardMovieAdapter = CardMovieAdapter()
        ActionMovieAdapter = CardMovieAdapter()

        binding.vg2Horror.apply {
            adapter = CardMovieAdapter
            setPageTransformer(SliderCard(0F, 0F))
        }

        binding.vg2Action.apply {
            adapter = ActionMovieAdapter
            setPageTransformer(SliderCard(8F, 8F))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.horrorMovie.collect() { movieState ->
                        when (movieState) {
                            is RemoteState.Success -> updateUI(movieState.data)
                            is RemoteState.Error -> Toast.makeText(
                                requireContext(),
                                "Failed fetch data",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                launch {
                    viewModel.actionMovie.collect() { movieState ->
                        when (movieState) {
                            is RemoteState.Success -> updateActionUI(movieState.data)
                            is RemoteState.Error -> Toast.makeText(
                                requireContext(),
                                "Failed fetch data",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }


            }
        }

        scrollDataHorror().let { newItemDetected ->
            binding.vg2Horror.registerOnPageChangeCallback(newItemDetected)
        }

        scrollDataAction().let { newItemDetected ->
            binding.vg2Action.registerOnPageChangeCallback(newItemDetected)
        }


    }

    private fun updateUI(movies: List<ResultsItem?>) {
        CardMovieAdapter.setList(movies)
    }

    private fun updateActionUI(movies: List<ResultsItem?>) {
        ActionMovieAdapter.setList(movies)
    }

    private fun scrollDataHorror(): ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == CardMovieAdapter.itemCount - 1) {
                    // Reached end of ViewPager, load more data
                    page++
                    loadMore = true
                    viewModel.getHorrorMovie(page, 27)
                }
            }
        }
    }

    private fun scrollDataAction(): ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == ActionMovieAdapter.itemCount - 1) {
                    // Reached end of ViewPager, load more data
                    page++
                    loadMore = true
                    viewModel.getActionMovie(page, 28)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}