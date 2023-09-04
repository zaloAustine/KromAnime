package com.zalo.kromanime.ui.animes


/**
Created by zaloaustine in 9/4/23.
 */
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zalo.kromanime.data.api.models.AnimeItem
import com.zalo.kromanime.databinding.AnimeItemBinding


class AnimeAdapter : PagingDataAdapter<AnimeItem, AnimeAdapter.AnimeViewHolder>(COMPARATOR) {

    inner class AnimeViewHolder(private val binding: AnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animeItem: AnimeItem) {
            binding.tvTitle.text = animeItem.title
            binding.tvSynopsis.text = animeItem.synopsis
            // Load the anime image using Glide
            Glide.with(itemView.context)
                .load(animeItem.trailer?.images?.image_url)
                .into(binding.ivAnime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = AnimeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val animeItem = getItem(position)
        if (animeItem != null) {
            holder.bind(animeItem)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<AnimeItem>() {
            override fun areItemsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean =
                oldItem.mal_id == newItem.mal_id

            override fun areContentsTheSame(oldItem: AnimeItem, newItem: AnimeItem): Boolean =
                oldItem == newItem
        }
    }
}
