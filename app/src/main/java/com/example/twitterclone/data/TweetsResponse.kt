package com.example.twitterclone.data

import com.example.twitterclone.Tweet
import com.google.gson.annotations.SerializedName

class TweetsResponse (
    @SerializedName("")
    var tweets: MutableList<Tweet>
        )

class TweetsResponseBuilder{
    lateinit var tweets: MutableList<Tweet>
    fun build(): TweetsResponse = TweetsResponse(
        tweets
    )
}



