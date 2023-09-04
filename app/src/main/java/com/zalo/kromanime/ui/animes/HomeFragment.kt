package com.zalo.kromanime.ui.animes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.zalo.kromanime.R
import com.zalo.kromanime.data.api.models.mappers.toAnimeItem
import com.zalo.kromanime.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

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
        initiateShimmer()
        setUpSearch()
        getAnimeList()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    private fun setUpAdapter() {
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2) //LinearLayoutManager(context)
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
                // Handle query submission
                if (query.isNullOrEmpty()) {
                    getAnimeList()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle query text changes
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
        binding.shimmerViewContainer.stopShimmer()
        binding.recyclerView.visibility = View.VISIBLE
        binding.shimmerViewContainer.visibility = View.GONE
    }

    private fun filter(query: String) {
        viewModel.getFilteredAnimeList(query).observe(viewLifecycleOwner) { animeList ->
            adapter.submitData(this.lifecycle, animeList.map { it.toAnimeItem() })
        }
    }
}