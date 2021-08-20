package com.example.twitterclone.data

import android.telecom.Call
import android.widget.Toast
import com.example.twitterclone.Tweet
import com.example.twitterclone.databinding.ActivityMainBinding
import com.example.twitterclone.presentation.MainActivity
import com.example.twitterclone.presentation.TweetsAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import twitter4j.ResponseList
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterFactory


interface TwitterAPI {

    @GET("tweets/search/recent?query=from:twitterdev")
    fun getTweets(): retrofit2.Call<TweetsResponse>

    val twitter: ResponseList<Status>?
        get() = TwitterFactory().instance
            .userTimeline


}

