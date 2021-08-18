package com.example.twitterclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TweetsAdapter (private val tweets: MutableList<Tweet>):
RecyclerView.Adapter<TweetsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tweet_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetsAdapter.ViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.bind(tweet)
    }

    override fun getItemCount(): Int = tweets.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(tweet: Tweet){
            itemView.findViewById<CardView>(R.id.view_tweet)
        }
    }
}