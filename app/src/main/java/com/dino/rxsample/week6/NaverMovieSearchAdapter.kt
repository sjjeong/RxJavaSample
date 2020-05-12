package com.dino.rxsample.week6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dino.rxsample.R
import com.dino.rxsample.week6.network.NaverMovieResponse
import kotlinx.android.synthetic.main.item_naver_movie.view.*

class NaverMovieSearchAdapter : RecyclerView.Adapter<NaverMovieSearchAdapter.ViewHolder>() {

    private val items = mutableListOf<NaverMovieResponse.Item>()

    fun resetAll(old: List<NaverMovieResponse.Item>) {
        items.clear()
        items.addAll(old)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent)

    override fun getItemCount() =
        items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  =
        holder.bind(items[position])

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_naver_movie, parent, false)
    ) {

        private val ivPoster = itemView.iv_poster
        private val tvTitle = itemView.tv_title
        private val tvSubtitle = itemView.tv_subtitle
        private val tvPubDate = itemView.tv_pub_date
        private val tvActor = itemView.tv_actor

        fun bind(item: NaverMovieResponse.Item) {
            Glide.with(ivPoster)
                .load(item.image)
                .into(ivPoster)

            tvTitle.text = item.title
            tvSubtitle.text = item.subtitle
            tvPubDate.text = item.pubDate
            tvActor.text = item.actor
        }
    }

}