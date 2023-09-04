package com.zalo.kromanime.ui.animes


/**
Created by zaloaustine in 9/4/23.
 */
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zalo.kromanime.R
import com.zalo.kromanime.data.api.models.animes.AnimeItem
import com.zalo.kromanime.databinding.AnimeItemBinding

class AnimeAdapter : PagingDataAdapter<AnimeItem, AnimeAdapter.AnimeViewHolder>(COMPARATOR) {

    inner class AnimeViewHolder(private val binding: AnimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(animeItem: AnimeItem) {

            val slideUpAnimation: Animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.slide_up)
            binding.animeTitle.startAnimation(slideUpAnimation)
            binding.animeTitle.text = animeItem.title

            if (animeItem.images?.jpg?.image_url != null) {
                Glide.with(itemView.context)
                    .load(animeItem.images.jpg.image_url)
                    .into(binding.ivAnime)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.rotate)
                    .apply(RequestOptions().fitCenter())
                    .into(binding.ivAnime)
            }
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
