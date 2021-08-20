package com.example.twitterclone.model.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.R
import com.example.twitterclone.Tweet
import com.squareup.picasso.Picasso

class TweetsAdapter (private val tweets: MutableList<Tweet>):
RecyclerView.Adapter<TweetsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tweet_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.bind(tweet)
    }

    override fun getItemCount(): Int = tweets.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(tweet: Tweet){
            Picasso.get().load(tweet.Image).into(itemView.findViewById<ImageView>(R.id.iv_user_photo))
            itemView.findViewById<TextView>(R.id.tv_user_name).text = tweet.Name
            itemView.findViewById<TextView>(R.id.tv_user_login).text = tweet.Login
            itemView.findViewById<TextView>(R.id.tv_tweet_time).text = tweet.Time.toString()
            itemView.findViewById<TextView>(R.id.tv_tweet_description).text = tweet.Description
            itemView.findViewById<TextView>(R.id.chip_coments).text = tweet.countComents.toString()
            itemView.findViewById<TextView>(R.id.chip_retweet).text = tweet.countRetweets.toString()
            itemView.findViewById<TextView>(R.id.chip_favority).text = tweet.countFavoriteds.toString()

        }
    }
}