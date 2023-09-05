package com.zalo.kromanime.ui.animes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zalo.kromanime.R
import com.zalo.kromanime.data.api.models.mappers.toAnimeItem
import com.zalo.kromanime.databinding.FragmentHomeBinding
import com.zalo.kromanime.utils.InternetUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: AnimeViewModel by viewModels()
    private val adapter = AnimeAdapter()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.swipeRefreshLayout.isRefreshing = true
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.amine_title)

        setUpAdapter()
        setUpSearch()
        getAnimeList()
        initiateShimmer()
        initObservers()
    }

    private fun initObservers() {
        viewModel.animeRefreshLiveEvent.observe(viewLifecycleOwner) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        InternetUtils.checkAndShowRetrySnackbar(this) { viewModel.refreshData() }
    }

    private fun setUpAdapter() {
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter

        viewModel.refreshData()
    }

    private fun getAnimeList() {
        viewModel.animeList.observe(this) { animeList ->
            binding.swipeRefreshLayout.isRefreshing = false
            adapter.submitData(this.lifecycle, animeList.map { it.toAnimeItem() })
        }
    }

    private fun setUpSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    getAnimeList()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText ?: ""
                filter(query)

                if (newText.isNullOrEmpty()) {
                    getAnimeList()
                }
                return true
            }
        })
    }

    private fun initiateShimmer() {
        binding.shimmerViewContainer.startShimmer()
        binding.recyclerView.visibility = View.GONE
        binding.searchView.visibility = View.GONE
        binding.shimmerViewContainer.visibility = View.VISIBLE

        lifecycleScope.launch {
            delay(1500)
            binding.shimmerViewContainer.stopShimmer()
            binding.recyclerView.visibility = View.VISIBLE
            binding.shimmerViewContainer.visibility = View.GONE
            binding.searchView.visibility = View.VISIBLE
        }
    }

    private fun filter(query: String) {
        viewModel.getFilteredAnimeList(query).observe(viewLifecycleOwner) { animeList ->
            adapter.submitData(this.lifecycle, animeList.map { it.toAnimeItem() })
        }
    }
}